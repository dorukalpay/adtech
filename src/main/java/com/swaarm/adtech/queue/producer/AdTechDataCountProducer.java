package com.swaarm.adtech.queue.producer;

import com.swaarm.adtech.data.model.Type;
import com.swaarm.adtech.queue.channel.QueueChannels;
import com.swaarm.adtech.queue.model.UpdateAdTechDataCountEvent;
import com.swaarm.adtech.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdTechDataCountProducer {

    private final QueueChannels dataCountChannel;

    public void updateAdDataCount(UUID advertisementRecordId, Type type) {
        log.info("Posting Update Data Count Event to Queue for AdvertisementId: {}", advertisementRecordId);
        UpdateAdTechDataCountEvent event = UpdateAdTechDataCountEvent.builder()
                .advertisementRecordId(advertisementRecordId)
                .recordType(type)
                .build();

        TransactionUtil.onCommit(() -> dataCountChannel.publish().send(MessageBuilder
                .withPayload(event)
                .setHeader("eventType", event.getType())
                .build()));
    }
}
