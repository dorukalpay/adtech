package com.swaarm.adtech.api.controller;

import com.swaarm.adtech.api.dto.request.AdStatisticsGroupRequest;
import com.swaarm.adtech.api.dto.response.AdStatisticsCategoryResponse;
import com.swaarm.adtech.api.dto.response.AdStatisticsResponse;
import com.swaarm.adtech.exception.BaseException;
import com.swaarm.adtech.exception.ErrorCode;
import com.swaarm.adtech.mapper.AdvertisementRecordStatisticsMapper;
import com.swaarm.adtech.service.AdvertisementStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping("/ads/statistics")
@RequiredArgsConstructor
public class AdvertisementStatisticsController {

    private final AdvertisementStatisticsService advertisementStatisticsService;

    private final AdvertisementRecordStatisticsMapper mapper;

    @GetMapping("time/{start}/{end}/overall")
    public AdStatisticsResponse listAllByDate(@PathVariable("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") OffsetDateTime start,
                                              @PathVariable("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") OffsetDateTime end) {
        checkIfValidDate(start, end);
        return mapper.outputToResponse(advertisementStatisticsService.listAllByDate(start, end));
    }

    @GetMapping("time/{start}/{end}")
    public AdStatisticsCategoryResponse listAllByCategories(@PathVariable("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") OffsetDateTime start,
                                                            @PathVariable("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") OffsetDateTime end,
                                                            AdStatisticsGroupRequest request) {
        checkIfValidDate(start, end);
        return mapper.outputToCategoryResponse(advertisementStatisticsService.listAllByCategories(start, end, request.getFields()));
    }

    private void checkIfValidDate(OffsetDateTime start, OffsetDateTime end) {
        if (start.isAfter(end)) {
            log.warn("Start date cannot be greater than end date! start: {}, end {}", start, end);
            throw new BaseException(ErrorCode.INVALID_REQUEST, String.format("Start date cannot be greater than end date! start: %s, end %s", start, end));
        }
    }

}
