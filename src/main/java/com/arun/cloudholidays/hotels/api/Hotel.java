package com.arun.cloudholidays.hotels.api;

public record Hotel(String name,
                    String location,
                    Integer star,
                    Integer numberOfRooms) {
}
