package com.swaarm.adtech.api.dto.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swaarm.adtech.api.dto.enums.BrowserDTO;
import com.swaarm.adtech.data.model.OperatingSystem;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchFieldsDTO {

    private Long advertisementId;

    private BrowserDTO browser;

    private UUID deliveryId;

    private UUID clickId;

    private UUID installId;

    @JsonProperty("os")
    private OperatingSystem operatingSystem;

    private String site;
}
