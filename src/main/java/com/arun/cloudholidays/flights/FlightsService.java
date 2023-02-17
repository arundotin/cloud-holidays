package com.arun.cloudholidays.flights;

import com.arun.cloudholidays.flights.api.FlightsApi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightsService {

    public List<FlightsApi> fetchAllFlights() {
        return dummyFlightsList();
    }

    public Optional<FlightsApi> fetchFlightMatchingFromAndTo(String from, String to) {
        return dummyFlightsList().stream()
                .filter(flight -> flightMatchingFromAndTo(flight,from, to))
                .findFirst();
    }


    private List<FlightsApi> dummyFlightsList() {
        return List.of(
                new FlightsApi("BOM", "DXB", "15:25 - 17:20", "01:55", "INR 11369"),
                new FlightsApi("CCJ", "DXB", "23:35 - 02:15", "02:40", "INR 11502"),
                new FlightsApi("MAA", "DXB", "06:10 - 09:15", "03:05", "INR 12127"),
                new FlightsApi("AMD", "DXB", "14:55 - 20:25", "05:30", "INR 12448"),
                new FlightsApi("BLR", "DXB", "10:15 - 20:25", "10:10", "INR 12716")
        );
    }

    private boolean flightMatchingFromAndTo(FlightsApi flight, String from, String to) {
        return flight.from().equalsIgnoreCase(from) && flight.to().equalsIgnoreCase(to);
    }


}
