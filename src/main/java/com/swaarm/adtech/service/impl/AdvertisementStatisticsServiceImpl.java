package com.swaarm.adtech.service.impl;

import com.swaarm.adtech.data.StatisticsSearchOutput;
import com.swaarm.adtech.data.model.Category;
import com.swaarm.adtech.service.AdvertisementStatisticsSearchService;
import com.swaarm.adtech.service.AdvertisementStatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdvertisementStatisticsServiceImpl implements AdvertisementStatisticsService {

    private final AdvertisementStatisticsSearchService advertisementStatisticsSearchService;

    @Override
    @Transactional(readOnly = true)
    public StatisticsSearchOutput listAllByDate(OffsetDateTime start, OffsetDateTime end) {
        log.info("Getting Statistics between {} and {}", start, end);
        return advertisementStatisticsSearchService.findAllByCategories(start, end, null).get(0);
    }

    @Override
    public List<StatisticsSearchOutput> listAllByCategories(OffsetDateTime start, OffsetDateTime end, List<Category> fields) {
        log.info("Getting Statistics for the categories {} between {} and {}", fields, start, end);
        return advertisementStatisticsSearchService.findAllByCategories(start, end, fields);
    }

}
