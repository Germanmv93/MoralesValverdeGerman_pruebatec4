package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;


import java.util.List;
import java.util.Optional;

public interface BookingHotelService {

    BookingHotelDto createBooking(BookingHotelDto bookingDto);

    String deleteBooking(Long id);

    List<BookingHotelDto> getAllBookingsDto();




}



