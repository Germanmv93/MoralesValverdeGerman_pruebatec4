package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {
    List<Hotel> findAllHotels();
    Hotel findHotelByCode(String hotelCode);
    Hotel saveHotel(Hotel hotel);
    void deleteHotel(String hotelCode);
    // Puedes añadir más métodos según los requisitos de tu aplicación.
    List<HotelDto> getAvailableHotels(LocalDate dateFrom, LocalDate dateTo, String destination);
    boolean existsByLocation(String location);

}

