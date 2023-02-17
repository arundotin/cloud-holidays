package com.arun.cloudholidays.flights;

import com.arun.cloudholidays.flights.api.FlightsApi;
import com.arun.cloudholidays.flights.api.FlightsResponse;
import com.arun.cloudholidays.flights.api.FlightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/flights")
public class FlightsController {

    private FlightsService flightsService;

    @Autowired
    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping
    public FlightsResponse getFlights() {
        var flights = flightsService.fetchAllFlights();
        return new FlightsResponse(flights);
    }

    @GetMapping(params = {"from", "to"})
    public ResponseEntity<FlightResponse> getFlightBetweenTwoPlaces(
            @RequestParam(value = "from", required = true)
            String from,
            @RequestParam(value = "to", required = true)
            String to) {

        var flight = flightsService.fetchFlightMatchingFromAndTo(from, to);

        if (!flight.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new FlightResponse(flight.get()));
    }


}

