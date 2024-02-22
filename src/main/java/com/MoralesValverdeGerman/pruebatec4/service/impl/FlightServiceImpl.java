package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import com.MoralesValverdeGerman.pruebatec4.exception.FlightAlreadyExistsException;
import com.MoralesValverdeGerman.pruebatec4.exception.FlightNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightBookingRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightRepository;
import com.MoralesValverdeGerman.pruebatec4.service.FlightService;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FlightDto createFlight(FlightDto flightDto) {


        Optional<Flight> existingFlight = flightRepository.findById(flightDto.getFlightCode());
        if (existingFlight.isPresent()) {
            throw new FlightAlreadyExistsException("The flight with flight code " + flightDto.getFlightCode() + " already exists.");
        }

        Flight flight = new Flight();
        BeanUtils.copyProperties(flightDto, flight);

        flight = flightRepository.save(flight);
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
        if (flights.isEmpty()) {
            throw new FlightNotFoundException("No flights found for the specified criteria.");
        }
        return flights.stream().map(flight -> {
            FlightDto dto = modelMapper.map(flight, FlightDto.class);
            int seatsReserved = flightBookingRepository.findByFlightFlightCode(flight.getFlightCode())
                    .stream()
                    .mapToInt(FlightBooking::getNumberOfPassenger)
                    .sum();
            dto.setSeats(flight.getSeats() - seatsReserved);
            return dto;
        }).collect(Collectors.toList());
    }

    public List<FlightDto> findFlights(LocalDate dateFrom, LocalDate dateTo, String origin, String destination) {
        List<Flight> flights = flightRepository.findByDateBetweenAndOriginAndDestination(dateFrom, dateTo, origin, destination);
        if (flights.isEmpty()) {
            throw new FlightNotFoundException("No flights found for the specified criteria.");
        }
        return flights.stream().map(flight -> modelMapper.map(flight, FlightDto.class)).collect(Collectors.toList());
    }

}

