package com.arun.cloudholidays.hotels;

import com.arun.cloudholidays.hotels.api.Hotel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelsService {

    public List<Hotel> fetchAllHotels() {
        return dummyHotelsList();
    }

    public List<Hotel> fetchHotelBasedOnLocation(String location) {
        return dummyHotelsList()
                .stream()
                .filter(hotel -> hotelMatchingTheLocation(hotel, location))
                .toList();
    }


    private List<Hotel> dummyHotelsList() {
        return List.of(
                new Hotel("Trident", "chennai", 5, 400),
                new Hotel("Leela Palace", "chennai", 5, 900),
                new Hotel("Lemon Tree", "chennai", 3, 200),
                new Hotel("Taj", "Mumbai", 5, 700),
                new Hotel("Apple Tree", "Delhi", 4, 700)
        );
    }


    private boolean hotelMatchingTheLocation(Hotel hotel, String location) {
        return hotel.location().equalsIgnoreCase(location);
    }


}
