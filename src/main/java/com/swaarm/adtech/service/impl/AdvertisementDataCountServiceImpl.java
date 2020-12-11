package com.swaarm.adtech.service.impl;

import com.swaarm.adtech.data.entity.AdvertisementDataCount;
import com.swaarm.adtech.data.entity.AdvertisementRecord;
import com.swaarm.adtech.data.model.Type;
import com.swaarm.adtech.data.repository.AdvertisementDataCountRepository;
import com.swaarm.adtech.data.repository.AdvertisementRecordRepository;
import com.swaarm.adtech.service.AdvertisementDataCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementDataCountServiceImpl implements AdvertisementDataCountService {

    private final AdvertisementRecordRepository advertisementRecordRepository;

    private final AdvertisementDataCountRepository advertisementDataCountRepository;

    @Override
    public void createOrUpdateDataCount(UUID advertisementRecordId, Type type) {
        log.info("Getting Advertisement Record with Advertisement Record Id: {}", advertisementRecordId);
        Optional<AdvertisementRecord> advertisementRecord = advertisementRecordRepository.findById(advertisementRecordId);
        if (advertisementRecord.isEmpty()) {
            log.warn("Advertisement Record with advertisementId: {} not exists!", advertisementRecordId);
            return;
        }

        advertisementDataCountRepository.findByAdvertisementRecordId(advertisementRecord.get().getId())
                .ifPresentOrElse(advertisementDataCount -> {
                    if (Type.DELIVERY.equals(type)) {
                        Long deliveryCount = advertisementDataCount.getDelivered();
                        advertisementDataCount.setDelivered(deliveryCount + 1);
                    } else if (Type.CLICK.equals(type)) {
                        Long clickCount = advertisementDataCount.getClicked();
                        advertisementDataCount.setClicked(clickCount + 1);
                    } else if (Type.INSTALL.equals(type)) {
                        Long installCount = advertisementDataCount.getInstalled();
                        advertisementDataCount.setInstalled(installCount + 1);
                    } else {
                        log.warn("Invalid Type: {}", type);
                        return;
                    }

                    log.info("Updating Advertisement Data Count for Advertisement Record Id: {}", advertisementRecordId);
                    advertisementDataCountRepository.save(advertisementDataCount);
                }, () -> {
                    AdvertisementDataCount dataCount = new AdvertisementDataCount();
                    dataCount.setAdvertisementRecordId(advertisementRecord.get().getId());
                    dataCount.setAdvertisementRecord(advertisementRecord.get());
                    dataCount.setDelivered(1L);
                    log.info("Creating Advertisement Data Count for Advertisement Record Id: {}", advertisementRecord.get().getId());
                    advertisementDataCountRepository.save(dataCount);
                });
    }

}
