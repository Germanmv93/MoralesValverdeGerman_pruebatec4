package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;

public interface FlightBookingService {
    FlightBooking bookFlight(FlightBookingDto bookingDto);
}
