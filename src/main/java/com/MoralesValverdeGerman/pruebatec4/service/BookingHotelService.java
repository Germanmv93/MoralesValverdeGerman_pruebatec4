package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;

import java.util.List;

public interface BookingHotelService {
    BookingHotel createBooking(BookingHotelDto bookingDto);
    String deleteBooking(Long id);

    List<BookingHotelDto> getAllBookingsDto();


}



