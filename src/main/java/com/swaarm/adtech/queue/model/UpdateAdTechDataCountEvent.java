package com.swaarm.adtech.queue.model;

import com.swaarm.adtech.data.model.Type;
import com.swaarm.adtech.queue.AdTechDataCountEvent;
import com.swaarm.adtech.queue.enums.AdTechDataCountEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdTechDataCountEvent implements AdTechDataCountEvent {

    private UUID advertisementRecordId;

    private Type recordType;

    @Override
    public AdTechDataCountEventType getType() {
        return AdTechDataCountEventType.UPDATE_DATA_COUNT;
    }
}
