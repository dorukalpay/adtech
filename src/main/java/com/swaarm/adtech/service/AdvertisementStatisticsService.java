package com.swaarm.adtech.service;

import com.swaarm.adtech.data.StatisticsSearchOutput;
import com.swaarm.adtech.data.model.Category;

import java.time.OffsetDateTime;
import java.util.List;

public interface AdvertisementStatisticsService {

    StatisticsSearchOutput listAllByDate(OffsetDateTime start, OffsetDateTime end);

    List<StatisticsSearchOutput> listAllByCategories(OffsetDateTime start, OffsetDateTime end, List<Category> fields);

}
