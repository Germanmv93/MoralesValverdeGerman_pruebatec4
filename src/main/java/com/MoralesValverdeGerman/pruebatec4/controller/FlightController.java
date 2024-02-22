package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightDto;
import com.MoralesValverdeGerman.pruebatec4.exception.FlightNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency/flights")
@Tag(name = "Flights Management", description = "API for flight operations")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    @Operation(summary = "Create a flight", description = "Creates a new flight with the provided details",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Flight created",
                            content = @Content(schema = @Schema(implementation = FlightDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto flightDto) {
        flightDto = flightService.createFlight(flightDto);
        return new ResponseEntity<>(flightDto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all flights", description = "Returns a list of all flights",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(schema = @Schema(implementation = FlightDto.class)))
            })
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> flights = flightService.findAllFlights();
        return ResponseEntity.ok(flights);
    }

    @DeleteMapping("/{flightCode}")
    @Operation(summary = "Delete a flight", description = "Deletes a flight with the specified flight code",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Flight deleted"),
                    @ApiResponse(responseCode = "404", description = "Flight not found")
            })
    public ResponseEntity<String> deleteFlight(@PathVariable String flightCode) {
        try {
            flightService.deleteFlight(flightCode);
            String message = String.format("The flight with flight code %s has been successfully deleted from the database.", flightCode);
            return ResponseEntity.ok(message);
        } catch (FlightNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("/agency/flights")
    @Operation(summary = "Search flights", description = "Search for flights between dates and locations",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Search successful",
                            content = @Content(schema = @Schema(implementation = FlightDto.class)))
            })
    public ResponseEntity<List<FlightDto>> getFlights(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination) {
        List<FlightDto> flights = flightService.findFlights(dateFrom, dateTo, origin, destination);
        return ResponseEntity.ok(flights);
    }
}

