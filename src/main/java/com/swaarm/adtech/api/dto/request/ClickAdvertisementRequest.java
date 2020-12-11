package com.swaarm.adtech.api.dto.request;

import com.swaarm.adtech.api.dto.request.base.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClickAdvertisementRequest extends BaseRequest {

    @NotNull
    private UUID deliveryId;

    @NotNull
    private UUID clickId;

    @NotNull
    private OffsetDateTime time;

}
