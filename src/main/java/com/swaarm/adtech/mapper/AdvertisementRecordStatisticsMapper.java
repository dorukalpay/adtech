package com.swaarm.adtech.mapper;

import com.swaarm.adtech.api.dto.model.CategorySearchDTO;
import com.swaarm.adtech.api.dto.model.SearchFieldsDTO;
import com.swaarm.adtech.api.dto.model.StatisticsDTO;
import com.swaarm.adtech.api.dto.model.TimeIntervalDTO;
import com.swaarm.adtech.api.dto.response.AdStatisticsCategoryResponse;
import com.swaarm.adtech.api.dto.response.AdStatisticsResponse;
import com.swaarm.adtech.data.StatisticsSearchOutput;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface AdvertisementRecordStatisticsMapper {

    default AdStatisticsResponse outputToResponse(StatisticsSearchOutput output) {
        AdStatisticsResponse response = new AdStatisticsResponse();
        response.setInterval(mapToTimeIntervalDTO(output));
        response.setStats(mapToStatisticsDTO(output));
        return response;
    }

    default AdStatisticsCategoryResponse outputToCategoryResponse(List<StatisticsSearchOutput> outputs) {
        AdStatisticsCategoryResponse response = new AdStatisticsCategoryResponse();

        for (StatisticsSearchOutput output : outputs) {
            CategorySearchDTO categorySearchDTO = new CategorySearchDTO();
            categorySearchDTO.setFields(mapToSearchFieldsDTO(output));
            categorySearchDTO.setStats(mapToStatisticsDTO(output));

            response.getData().add(categorySearchDTO);
            response.setInterval(mapToTimeIntervalDTO(output));
        }

        return response;
    }

    SearchFieldsDTO mapToSearchFieldsDTO(StatisticsSearchOutput output);

    TimeIntervalDTO mapToTimeIntervalDTO(StatisticsSearchOutput output);

    StatisticsDTO mapToStatisticsDTO(StatisticsSearchOutput output);
}
