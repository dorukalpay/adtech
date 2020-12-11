package com.swaarm.adtech.data.repository;

import com.swaarm.adtech.data.entity.AdvertisementRecordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdvertisementRecordDetailRepository extends JpaRepository<AdvertisementRecordDetail, UUID> {

    boolean existsByDeliveryId(UUID deliveryId);

    boolean existsByClickId(UUID clickId);

    boolean existsByInstallId(UUID installId);

    Optional<AdvertisementRecordDetail> findFirstByDeliveryId(UUID deliveryId);

    List<AdvertisementRecordDetail> findAllByClickId(UUID clickId);

}
