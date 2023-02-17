package com.arun.cloudholidays.hotels;

import com.arun.cloudholidays.hotels.api.HotelsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/hotels")
public class HotelsController {

    private HotelsService hotelsService;


    @Autowired
    public HotelsController(HotelsService hotelsService) {
        this.hotelsService = hotelsService;
    }

    @GetMapping
    public HotelsResponse getAllHotels() {
        var hotels = hotelsService.fetchAllHotels();
        return new HotelsResponse(hotels);
    }

    @GetMapping(params = {"location"})
    public HotelsResponse getHotelsBasedOnLocation(
            @RequestParam(value = "location", required = true)
            String location) {

        var hotelsBasedOnLocation = hotelsService.fetchHotelBasedOnLocation(location);

        return new HotelsResponse(hotelsBasedOnLocation);
    }




}

