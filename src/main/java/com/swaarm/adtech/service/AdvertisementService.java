package com.swaarm.adtech.service;

import com.swaarm.adtech.api.dto.request.ClickAdvertisementRequest;
import com.swaarm.adtech.api.dto.request.DeliveryAdvertisementRequest;
import com.swaarm.adtech.api.dto.request.InstallAdvertisementRequest;

public interface AdvertisementService {

    void createDeliveredAdvertisementRecord(DeliveryAdvertisementRequest request);

    void createClickedAdvertisementRecord(ClickAdvertisementRequest request);

    void createInstalledAdvertisementRecord(InstallAdvertisementRequest request);
}
