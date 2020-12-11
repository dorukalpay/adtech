package com.swaarm.adtech.mapper;

import com.swaarm.adtech.api.dto.request.DeliveryAdvertisementRequest;
import com.swaarm.adtech.data.entity.AdvertisementRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdvertisementRecordMapper {

    AdvertisementRecord dtoToEntity(DeliveryAdvertisementRequest request);

}
