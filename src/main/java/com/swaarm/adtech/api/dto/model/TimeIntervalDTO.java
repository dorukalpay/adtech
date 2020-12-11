package com.swaarm.adtech.api.dto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeIntervalDTO {

    private OffsetDateTime start;

    private OffsetDateTime end;
}
