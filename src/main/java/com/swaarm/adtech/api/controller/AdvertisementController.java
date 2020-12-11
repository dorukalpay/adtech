package com.swaarm.adtech.api.controller;

import com.swaarm.adtech.api.dto.request.ClickAdvertisementRequest;
import com.swaarm.adtech.api.dto.request.DeliveryAdvertisementRequest;
import com.swaarm.adtech.api.dto.request.InstallAdvertisementRequest;
import com.swaarm.adtech.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/delivery")
    @ResponseStatus(HttpStatus.OK)
    public void createDelivery(@Valid @RequestBody DeliveryAdvertisementRequest request) {
        log.info("Creating Delivery Advertisement.");
        advertisementService.createDeliveredAdvertisementRecord(request);
    }

    @PostMapping("/click")
    @ResponseStatus(HttpStatus.OK)
    public void createClick(@Valid @RequestBody ClickAdvertisementRequest request) {
        log.info("Creating Click Advertisement.");
        advertisementService.createClickedAdvertisementRecord(request);
    }

    @PostMapping("/install")
    @ResponseStatus(HttpStatus.OK)
    public void createInstall(@Valid @RequestBody InstallAdvertisementRequest request) {
        log.info("Creating Install Advertisement.");
        advertisementService.createInstalledAdvertisementRecord(request);
    }
}
