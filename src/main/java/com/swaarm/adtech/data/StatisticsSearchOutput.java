package com.swaarm.adtech.data;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class StatisticsSearchOutput {

    private Long advertisementId;

    private String browser;

    private String operatingSystem;

    private String site;

    private Long deliveries;

    private Long clicks;

    private Long installs;

    private OffsetDateTime start;

    private OffsetDateTime end;

}
