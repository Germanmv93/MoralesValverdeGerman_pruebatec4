package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;

import java.util.List;
import java.util.Optional;

public interface BookingHotelService {

    BookingHotelDto createBooking(BookingHotelDto bookingDto);
//    BookingHotel createBooking(BookingHotelDto bookingDto);
    String deleteBooking(Long id);

    List<BookingHotelDto> getAllBookingsDto();




}



