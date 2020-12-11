package com.swaarm.adtech.api.dto.response;

import com.swaarm.adtech.api.dto.model.StatisticsDTO;
import com.swaarm.adtech.api.dto.model.TimeIntervalDTO;
import lombok.Data;

@Data
public class AdStatisticsResponse {

    private TimeIntervalDTO interval;

    private StatisticsDTO stats;

}
