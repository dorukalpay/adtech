package com.swaarm.adtech.data.repository;

import com.swaarm.adtech.data.entity.AdvertisementDataCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdvertisementDataCountRepository extends JpaRepository<AdvertisementDataCount, Long> {

    Optional<AdvertisementDataCount> findByAdvertisementRecordId(UUID advertisementRecordId);
}
