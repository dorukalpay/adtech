package com.swaarm.adtech.config;

import com.swaarm.adtech.queue.channel.QueueChannels;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(QueueChannels.class)
public class QueueConfig {

}
