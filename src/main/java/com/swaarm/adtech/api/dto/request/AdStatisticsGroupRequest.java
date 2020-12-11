package com.swaarm.adtech.api.dto.request;

import com.swaarm.adtech.data.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class AdStatisticsGroupRequest {

    private List<Category> fields;
}
