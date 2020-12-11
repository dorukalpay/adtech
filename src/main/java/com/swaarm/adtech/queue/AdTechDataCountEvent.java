package com.swaarm.adtech.queue;

import com.swaarm.adtech.queue.enums.AdTechDataCountEventType;

public interface AdTechDataCountEvent {

    AdTechDataCountEventType getType();
}
