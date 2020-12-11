package com.swaarm.adtech.service;

import com.swaarm.adtech.data.model.Type;

import java.util.UUID;

public interface AdvertisementDataCountService {

    void createOrUpdateDataCount(UUID advertisementRecordId, Type type);
}
