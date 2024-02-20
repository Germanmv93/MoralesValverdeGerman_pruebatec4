package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.dto.PassengerDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import com.MoralesValverdeGerman.pruebatec4.entity.Passenger;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightBookingRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.FlightRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.PassengerRepository;
import com.MoralesValverdeGerman.pruebatec4.service.FlightBookingService;
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

    @Transactional
    @Override
    public FlightBooking bookFlight(FlightBookingDto bookingDto) {
        Flight flight = flightRepository.findById(bookingDto.getFlightCode())
                .orElseThrow(() -> new IllegalArgumentException("Flight not found with code: " + bookingDto.getFlightCode()));

        // Calcula los asientos ya reservados
        int seatsReserved = flight.getBookings().stream()
                .mapToInt(FlightBooking::getNumberOfPassenger)
                .sum();

        // Verifica si hay suficientes asientos disponibles
        if (bookingDto.getNumberOfPersons() + seatsReserved > flight.getSeats()) {
            throw new IllegalStateException("Not enough seats available.");
        }

        // Crea la reserva
        FlightBooking booking = new FlightBooking();
        booking.setNumberOfPassenger(bookingDto.getNumberOfPersons());
        booking.setFlight(flight);
        // Configura los pasajeros aquí
        booking.setPassengers(convertPassengersDtoToEntities(bookingDto.getPassengers(), booking));

        // Guarda la reserva
        return flightBookingRepository.save(booking);
    }

    private List<Passenger> convertPassengersDtoToEntities(List<PassengerDto> passengerDtos, FlightBooking booking) {
        return passengerDtos.stream().map(dto -> {
            Passenger passenger = new Passenger();
            passenger.setDni(dto.getDni());
            passenger.setName(dto.getName());
            passenger.setSurName(dto.getSurName());
            passenger.setBooking(booking);
            return passenger;
        }).collect(Collectors.toList());
    }
}


