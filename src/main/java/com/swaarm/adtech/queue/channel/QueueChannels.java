package com.swaarm.adtech.queue.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface QueueChannels {

    String AD_TECH_DATA_COUNT_CONSUMER = "data-count-consumer";

    String AD_TECH_DATA_COUNT_PRODUCER = "data-count-producer";

    @Input(AD_TECH_DATA_COUNT_CONSUMER)
    SubscribableChannel receive();

    @Output(AD_TECH_DATA_COUNT_PRODUCER)
    MessageChannel publish();
}
