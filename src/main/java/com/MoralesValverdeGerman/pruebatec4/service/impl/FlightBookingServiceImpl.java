package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.dto.PassengerDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import com.MoralesValverdeGerman.pruebatec4.entity.Passenger;
import com.MoralesValverdeGerman.pruebatec4.exception.*;
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

        if (!flight.getOrigin().equals(bookingDto.getOrigin()) || !flight.getDestination().equals(bookingDto.getDestination())) {
            throw new LocationMismatchException("Origin and destination do not match flight details.");
        }

        if (bookingDto.getNumberOfPassenger() != bookingDto.getPassengers().size()) {
            throw new PassengerCountMismatchException("Number of passengers does not match the provided passenger details.");
        }


        // Ajuste en la línea siguiente: usa getNumberOfPassenger() en lugar de getNumberOfPersons()
        int seatsReserved = flightBookingRepository.countByFlightFlightCode(bookingDto.getFlightCode());
        if (bookingDto.getNumberOfPassenger() + seatsReserved > flight.getSeats()) {
            throw new InsufficientSeatsException("Not enough seats available.");
        }

        FlightBooking booking = modelMapper.map(bookingDto, FlightBooking.class);
        booking.setFlight(flight);
        double totalPrice = flight.getPricePerSeat() * bookingDto.getNumberOfPassenger();

        booking.setTotalPrice(totalPrice); // Asegúrate de que FlightBooking tenga un campo para totalPrice

        List<Passenger> passengers = bookingDto.getPassengers().stream()
                .map(dto -> modelMapper.map(dto, Passenger.class))
                .peek(passenger -> passenger.setBooking(booking))
                .collect(Collectors.toList());
        booking.setPassengers(passengers);
        FlightBooking savedBooking = flightBookingRepository.save(booking);
        return modelMapper.map(savedBooking, FlightBookingDto.class);
    }

    @Transactional
    @Override
    public String deleteFlightBooking(Long bookingId) {
        FlightBooking booking = flightBookingRepository.findById(bookingId)
                .orElseThrow(() -> new FlightBookingNotFoundException("No flight booking found with id: " + bookingId));

        // Elimina los pasajeros asociados a la reserva
        passengerRepository.deleteByBookingId(bookingId);

        // Elimina la reserva de vuelo
        flightBookingRepository.delete(booking);

        return "Your flight booking has been successfully deleted.";
    }


}


