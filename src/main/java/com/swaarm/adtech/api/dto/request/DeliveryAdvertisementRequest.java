package com.swaarm.adtech.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swaarm.adtech.api.dto.enums.BrowserDTO;
import com.swaarm.adtech.api.dto.enums.OperatingSystemDTO;
import com.swaarm.adtech.api.dto.request.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeliveryAdvertisementRequest extends BaseRequest {

    @NotNull
    private Long advertisementId;

    @NotNull
    private UUID deliveryId;

    @NotNull
    private OffsetDateTime time;

    @NotNull
    private BrowserDTO browser;

    @NotNull
    @JsonProperty("os")
    private OperatingSystemDTO operatingSystem;

    @NotNull
    private String site;

    @AssertTrue(message = "Not a Valid Website URL")
    public boolean isValidURI() {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(site);
    }
}
