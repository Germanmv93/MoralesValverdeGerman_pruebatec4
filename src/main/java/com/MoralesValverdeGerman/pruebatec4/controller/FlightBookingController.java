package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import com.MoralesValverdeGerman.pruebatec4.service.FlightBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agency/flight-booking")
@Tag(name = "Flight Booking", description = "API for flight booking operations")
public class FlightBookingController {

    @Autowired
    private FlightBookingService flightBookingService;

    @PostMapping("/new")
    @Operation(summary = "Create a flight booking", description = "Books a flight and returns the booking details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Flight booking created",
                            content = @Content(schema = @Schema(implementation = FlightBookingDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            })
    public ResponseEntity<FlightBookingDto> createFlightBooking(@RequestBody FlightBookingDto bookingDto) {
        FlightBookingDto createdBooking = flightBookingService.bookFlight(bookingDto);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
}
