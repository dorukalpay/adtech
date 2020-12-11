package com.swaarm.adtech.data.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category {

    ID("advertisement_id", "advertisementId"),
    BROWSER("browser", "browser"),
    OS("operating_system", "operatingSystem"),
    SITE("site", "site");

    @Getter
    private final String columnName;

    @Getter
    private final String fieldName;
}
