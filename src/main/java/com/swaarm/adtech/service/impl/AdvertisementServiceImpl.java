package com.swaarm.adtech.service.impl;

import com.swaarm.adtech.api.dto.request.ClickAdvertisementRequest;
import com.swaarm.adtech.api.dto.request.DeliveryAdvertisementRequest;
import com.swaarm.adtech.api.dto.request.InstallAdvertisementRequest;
import com.swaarm.adtech.data.entity.AdvertisementRecord;
import com.swaarm.adtech.data.entity.AdvertisementRecordDetail;
import com.swaarm.adtech.data.model.Type;
import com.swaarm.adtech.data.repository.AdvertisementRecordDetailRepository;
import com.swaarm.adtech.data.repository.AdvertisementRecordRepository;
import com.swaarm.adtech.exception.BaseException;
import com.swaarm.adtech.exception.ErrorCode;
import com.swaarm.adtech.mapper.AdvertisementRecordMapper;
import com.swaarm.adtech.queue.producer.AdTechDataCountProducer;
import com.swaarm.adtech.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRecordRepository advertisementRecordRepository;

    private final AdvertisementRecordDetailRepository advertisementRecordDetailRepository;

    private final AdvertisementRecordMapper advertisementRecordMapper;

    private final AdTechDataCountProducer adTechDataCountProducer;

    @Override
    @Transactional
    public void createDeliveredAdvertisementRecord(DeliveryAdvertisementRequest request) {
        // deliveryIds must be unique for type DELIVERY
        if (advertisementRecordDetailRepository.existsByDeliveryId(request.getDeliveryId())) {
            log.warn("Advertisement Record with deliveryId: {} already exists.", request.getDeliveryId());
            throw new BaseException(ErrorCode.ALREADY_EXIST, String.format("Advertisement Record with deliveryId: %s already exists.", request.getDeliveryId()));
        }
        UUID advertisementRecordId;
        AdvertisementRecord advertisementRecord = advertisementRecordMapper.dtoToEntity(request);
        AdvertisementRecord existingRecord = advertisementRecordRepository
                .findByAdvertisementIdAndBrowserAndOperatingSystemAndSite(request.getAdvertisementId(), advertisementRecord.getBrowser(), advertisementRecord.getOperatingSystem(), request.getSite());

        if (existingRecord == null) {
            AdvertisementRecordDetail detail = new AdvertisementRecordDetail();
            detail.setType(Type.DELIVERY);
            detail.setDeliveryId(request.getDeliveryId());
            detail.setTime(request.getTime());
            detail.setAdvertisementRecord(advertisementRecord);
            advertisementRecord.getDetails().add(detail);

            log.info("Saving Delivered Advertisement Record with advertisementId: {} and deliveryId: {}", request.getAdvertisementId(), request.getDeliveryId());
            AdvertisementRecord saved = advertisementRecordRepository.save(advertisementRecord);
            advertisementRecordId = saved.getId();
        } else {
            AdvertisementRecordDetail detail = new AdvertisementRecordDetail();
            advertisementRecordId = existingRecord.getId();
            detail.setType(Type.DELIVERY);
            detail.setDeliveryId(request.getDeliveryId());
            detail.setTime(request.getTime());
            detail.setAdvertisementRecordId(advertisementRecordId);
            detail.setAdvertisementRecord(existingRecord);

            log.info("Saving Delivered Advertisement Record Detail with advertisementId: {} and deliveryId: {}", request.getAdvertisementId(), request.getDeliveryId());
            advertisementRecordDetailRepository.save(detail);
        }

        adTechDataCountProducer.updateAdDataCount(advertisementRecordId, Type.DELIVERY);
    }

    @Override
    @Transactional
    public void createClickedAdvertisementRecord(ClickAdvertisementRequest request) {
        // Same Delivery can be clicked multiple times
        // There can be multiple unique clickIds for the same deliveryId
        Optional<AdvertisementRecordDetail> result = advertisementRecordDetailRepository.findFirstByDeliveryId(request.getDeliveryId());
        if (result.isEmpty()) {
            log.warn("Advertisement Record with deliveryId: {} not exists.", request.getDeliveryId());
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, String.format("Advertisement Record with deliveryId: %s not exists.", request.getDeliveryId()));
        }

        // ClickId must be unique
        if (advertisementRecordDetailRepository.existsByClickId(request.getClickId())) {
            log.warn("Advertisement Record with clickId: {} already exists.", request.getClickId());
            throw new BaseException(ErrorCode.ALREADY_EXIST, String.format("Advertisement Record with clickId: %s already exists.", request.getClickId()));
        }

        AdvertisementRecord advertisementRecord = result.get().getAdvertisementRecord();

        AdvertisementRecordDetail detail = new AdvertisementRecordDetail();
        detail.setType(Type.CLICK);
        detail.setDeliveryId(request.getDeliveryId());
        detail.setClickId(request.getClickId());
        detail.setTime(request.getTime());
        detail.setAdvertisementRecordId(advertisementRecord.getId());
        detail.setAdvertisementRecord(advertisementRecord);

        log.info("Saving Clicked Advertisement Detail Record with deliveryId: {} and clickId: {}", request.getDeliveryId(), request.getClickId());
        advertisementRecordDetailRepository.save(detail);

        adTechDataCountProducer.updateAdDataCount(advertisementRecord.getId(), Type.CLICK);
    }

    @Override
    @Transactional
    public void createInstalledAdvertisementRecord(InstallAdvertisementRequest request) {
        // There can be at most 2 same clickIds,
        // one is for CLICK Type and the other one is for INSTALL Type
        List<AdvertisementRecordDetail> existingDetailRecords = advertisementRecordDetailRepository.findAllByClickId(request.getClickId());
        if (CollectionUtils.isEmpty(existingDetailRecords)) {
            log.warn("Advertisement Record with clickId: {} not exists.", request.getClickId());
            throw new BaseException(ErrorCode.RESOURCE_NOT_FOUND, String.format("Advertisement Record with clickId: %s not exists.", request.getClickId()));
        }

        // Same clickId can be only installed once
        if (existingDetailRecords.stream().anyMatch(record -> Type.INSTALL.equals(record.getType()))) {
            log.warn("Advertisement Record with clickId: {} can only be installed once.", request.getClickId());
            throw new BaseException(ErrorCode.INVALID_REQUEST, String.format("Advertisement Record with clickId: %s can only be installed once.", request.getClickId()));
        }

        // InstallId must be unique
        if (advertisementRecordDetailRepository.existsByInstallId(request.getInstallId())) {
            log.warn("Advertisement Record with installId: {} already exists.", request.getClickId());
            throw new BaseException(ErrorCode.INVALID_REQUEST, String.format("Advertisement Record with installId: %s already exists.", request.getInstallId()));
        }

        AdvertisementRecordDetail recordDetail = existingDetailRecords.get(0);
        AdvertisementRecord advertisementRecord = recordDetail.getAdvertisementRecord();

        AdvertisementRecordDetail detail = new AdvertisementRecordDetail();
        detail.setType(Type.INSTALL);
        detail.setDeliveryId(recordDetail.getDeliveryId());
        detail.setClickId(request.getClickId());
        detail.setInstallId(request.getInstallId());
        detail.setTime(request.getTime());
        detail.setAdvertisementRecord(advertisementRecord);
        detail.setAdvertisementRecordId(advertisementRecord.getId());

        log.info("Saving Installed Advertisement Detail Record with clickId: {} and installId: {}", request.getClickId(), request.getInstallId());
        advertisementRecordDetailRepository.save(detail);

        adTechDataCountProducer.updateAdDataCount(advertisementRecord.getId(), Type.INSTALL);
    }

}
