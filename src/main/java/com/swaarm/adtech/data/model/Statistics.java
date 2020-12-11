package com.swaarm.adtech.data.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Statistics {

    private Long deliveries;

    private Long clicks;

    private Long installs;

}
