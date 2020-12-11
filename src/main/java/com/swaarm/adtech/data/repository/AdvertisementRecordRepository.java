package com.swaarm.adtech.data.repository;

import com.swaarm.adtech.data.entity.AdvertisementRecord;
import com.swaarm.adtech.data.model.Browser;
import com.swaarm.adtech.data.model.OperatingSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdvertisementRecordRepository extends JpaRepository<AdvertisementRecord, UUID> {

    AdvertisementRecord findByAdvertisementIdAndBrowserAndOperatingSystemAndSite(Long advertisementId, Browser browser, OperatingSystem operatingSystem, String site);

}
