package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import com.MoralesValverdeGerman.pruebatec4.exception.FlightNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightRepository;
import com.MoralesValverdeGerman.pruebatec4.service.FlightService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public FlightDto createFlight(FlightDto flightDto) {
        // Verificar si el vuelo ya existe en la base de datos
        Optional<Flight> existingFlight = flightRepository.findById(flightDto.getFlightCode());
        if (existingFlight.isPresent()) {
            // Si el vuelo ya existe, lanzar una excepción con el mensaje deseado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The flight with flight code " + flightDto.getFlightCode() + " already exists.");
        }

        // Si el vuelo no existe, proceder a crearlo
        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);
        // La siguiente línea guarda el vuelo en la base de datos
        flight = flightRepository.save(flight);
        // Actualizar el DTO con información posiblemente actualizada durante el guardado
        BeanUtils.copyProperties(flight, flightDto);
        return flightDto;
    }


    public void deleteFlight(String flightCode) {
        Flight flight = flightRepository.findById(flightCode)
                .orElseThrow(() -> new FlightNotFoundException("The flight with code " + flightCode + " does not exist."));
        flightRepository.delete(flight);
    }

    @Override
    public List<FlightDto> findAllFlights() {
        List<Flight> flights = flightRepository.findAll();
        // Convertir lista de Flight a FlightDto. Asumiendo que tienes un método para convertir entidad a DTO.
        return flights.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private FlightDto convertToDto(Flight flight) {
        FlightDto dto = new FlightDto();
        dto.setFlightCode(flight.getFlightCode());
        dto.setDate(flight.getDate());
        dto.setOrigin(flight.getOrigin());
        dto.setDestination(flight.getDestination());
        dto.setSeats(flight.getSeats());
        dto.setSeatType(flight.getSeatType());
        // Aquí puedes añadir la lógica para asignar el resto de los campos necesarios
        return dto;
    }

}

