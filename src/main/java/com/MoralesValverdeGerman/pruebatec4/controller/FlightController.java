package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightDto;
import com.MoralesValverdeGerman.pruebatec4.exception.FlightNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency/flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping
    public ResponseEntity<FlightDto> createFlight(@RequestBody FlightDto flightDto) {
        flightDto = flightService.createFlight(flightDto);
        return new ResponseEntity<>(flightDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        List<FlightDto> flights = flightService.findAllFlights();
        return ResponseEntity.ok(flights);
    }

    @DeleteMapping("/{flightCode}")
    public ResponseEntity<String> deleteFlight(@PathVariable String flightCode) {
        try {
            flightService.deleteFlight(flightCode);
            String message = String.format("The flight with flight code %s has been successfully deleted from the database.", flightCode);
            return ResponseEntity.ok(message);
        } catch (FlightNotFoundException ex) {
            // Aquí puedes manejar el error de forma más específica, por ejemplo, devolviendo un estado HTTP 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}

