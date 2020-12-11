package com.swaarm.adtech.queue.consumer;

import com.swaarm.adtech.queue.channel.QueueChannels;
import com.swaarm.adtech.queue.model.UpdateAdTechDataCountEvent;
import com.swaarm.adtech.service.AdvertisementDataCountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdTechDataCountConsumer {

    private final AdvertisementDataCountService advertisementDataCountService;

    @StreamListener(target = QueueChannels.AD_TECH_DATA_COUNT_CONSUMER, condition = "headers['eventType'] == 'UPDATE_DATA_COUNT'")
    public void createOrUpdateAdTechDataCount(UpdateAdTechDataCountEvent event) {
        log.info("Received UpdateAdTechDataCountEvent with Advertisement Record Id: {} and Type: {}", event.getAdvertisementRecordId(), event.getType());
        advertisementDataCountService.createOrUpdateDataCount(event.getAdvertisementRecordId(), event.getRecordType());
    }
}
