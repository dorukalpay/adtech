package com.swaarm.adtech.api.dto.model;

import lombok.Data;

@Data
public class CategorySearchDTO {

    private SearchFieldsDTO fields;

    private StatisticsDTO stats;
}
