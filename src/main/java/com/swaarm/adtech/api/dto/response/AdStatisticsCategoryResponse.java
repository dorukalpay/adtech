package com.swaarm.adtech.api.dto.response;

import com.swaarm.adtech.api.dto.model.CategorySearchDTO;
import com.swaarm.adtech.api.dto.model.TimeIntervalDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdStatisticsCategoryResponse {

    private TimeIntervalDTO interval;

    private List<CategorySearchDTO> data = new ArrayList<>();
}
