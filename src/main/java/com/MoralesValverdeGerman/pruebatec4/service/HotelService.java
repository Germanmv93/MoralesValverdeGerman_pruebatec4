package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.HotelPatchDto;

import java.time.LocalDate;
import java.util.List;

public interface HotelService {

    List<HotelDto> getHotels(LocalDate dateFrom, LocalDate dateTo, String destination);
    HotelDto getHotelDetails(String hotelCode);
    List<HotelDto> findAllHotels();
    HotelDto findHotelByCode(String hotelCode);
    HotelDto createHotel(HotelDto hotelDto);
    void deleteHotel(String hotelCode);
    boolean existsByLocation(String location);
    HotelDto updateHotel(String hotelCode, HotelPatchDto hotelPatchDto);
}
