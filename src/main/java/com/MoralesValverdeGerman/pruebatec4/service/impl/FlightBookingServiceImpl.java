package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.dto.PassengerDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import com.MoralesValverdeGerman.pruebatec4.entity.Passenger;
import com.MoralesValverdeGerman.pruebatec4.exception.FlightNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.exception.InsufficientSeatsException;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightBookingRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.PassengerRepository;
import com.MoralesValverdeGerman.pruebatec4.service.FlightBookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightBookingServiceImpl implements FlightBookingService {

    @Autowired
    private FlightBookingRepository flightBookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    @Override
    public FlightBookingDto bookFlight(FlightBookingDto bookingDto) {
        Flight flight = flightRepository.findByFlightCode(bookingDto.getFlightCode())
                .orElseThrow(() -> new FlightNotFoundException("Flight not found with code: " + bookingDto.getFlightCode()));

        // Ajuste en la línea siguiente: usa getNumberOfPassenger() en lugar de getNumberOfPersons()
        int seatsReserved = flightBookingRepository.countByFlightFlightCode(bookingDto.getFlightCode());
        if (bookingDto.getNumberOfPassenger() + seatsReserved > flight.getSeats()) {
            throw new InsufficientSeatsException("Not enough seats available.");
        }

        // Convierte directamente FlightBookingDto a FlightBooking
        FlightBooking booking = modelMapper.map(bookingDto, FlightBooking.class);
        booking.setFlight(flight); // Asegúrate de que el mapeo conserva la relación correcta

        // Procesa y asigna los pasajeros
        List<Passenger> passengers = bookingDto.getPassengers().stream()
                .map(dto -> modelMapper.map(dto, Passenger.class))
                .peek(passenger -> passenger.setBooking(booking))
                .collect(Collectors.toList());
        booking.setPassengers(passengers);

        // Guarda la reserva
        FlightBooking savedBooking = flightBookingRepository.save(booking);

        // Convierte la reserva guardada de vuelta a DTO para la respuesta
        return modelMapper.map(savedBooking, FlightBookingDto.class);
    }


}


