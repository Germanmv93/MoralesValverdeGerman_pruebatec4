package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.HotelPatchDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelAlreadyExistsException;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import com.MoralesValverdeGerman.pruebatec4.utils.HotelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> findAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel findHotelByCode(String hotelCode) {
        return hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException(hotelCode));
    }

    public Hotel saveHotel(Hotel hotel) {
        if (hotelRepository.findByHotelCode(hotel.getHotelCode()).isPresent()) {
            throw new HotelAlreadyExistsException("Sorry, the hotel you are trying to create already exists in the database, the operation could not be performed.");
        }
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with code: " + hotelCode));
        hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelDto> getAvailableHotels(LocalDate dateFrom, LocalDate dateTo, String destination) {
        List<Hotel> hotels = hotelRepository.findAvailableHotelsByDateRangeAndDestination(dateFrom, dateTo, destination);
        return hotels.stream()
                .map(HotelUtils::convertToHotelDto) // Cambia aquí a la llamada estática
                .collect(Collectors.toList());
    }


    public boolean existsByLocation(String location) {
        return hotelRepository.existsByLocation(location);
    }

    @Override
    public HotelDto updateHotel(String hotelCode, HotelPatchDto hotelPatchDto) {
        Hotel hotel = hotelRepository.findById(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with code: " + hotelCode));

        hotelPatchDto.getName().ifPresent(hotel::setName);
        hotelPatchDto.getLocation().ifPresent(hotel::setLocation);

        Hotel updatedHotel = hotelRepository.save(hotel);

        // Utiliza HotelUtils para convertir la entidad Hotel a HotelDto
        return HotelUtils.convertToHotelDto(updatedHotel);
    }


}
