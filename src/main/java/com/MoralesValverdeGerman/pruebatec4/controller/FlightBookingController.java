package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import com.MoralesValverdeGerman.pruebatec4.service.FlightBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agency/flight-booking")
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @PostMapping("/new")
    public ResponseEntity<FlightBookingDto> createFlightBooking(@RequestBody FlightBookingDto bookingDto) {
        FlightBookingDto createdBooking = flightBookingService.bookFlight(bookingDto);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
}
