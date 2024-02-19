package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightDto;

import java.util.List;

public interface FlightService {
    FlightDto createFlight(FlightDto flightDto);
    void deleteFlight(String flightCode);
    List<FlightDto> findAllFlights();
}

