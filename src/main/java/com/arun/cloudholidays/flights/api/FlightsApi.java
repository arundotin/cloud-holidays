package com.arun.cloudholidays.flights.api;

import java.util.List;


    public record FlightsApi(String from,
                         String to,
                         String departure,
                         String duration,
                         String fare) {
    }

