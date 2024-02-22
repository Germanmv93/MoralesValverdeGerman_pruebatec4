package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.HotelPatchDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelAlreadyExistsException;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HotelDto> findAllHotels() {
        return hotelRepository.findAll().stream()
                .map(hotel -> modelMapper.map(hotel, HotelDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public HotelDto findHotelByCode(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException(hotelCode));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {
        if (hotelRepository.existsByHotelCode(hotelDto.getHotelCode())) {
            throw new HotelAlreadyExistsException("Hotel already exists.");
        }
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        return modelMapper.map(hotelRepository.save(hotel), HotelDto.class);
    }

    @Override
    public void deleteHotel(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with code: " + hotelCode));
        hotelRepository.delete(hotel);
    }

    @Override
    public boolean existsByLocation(String location) {
        return hotelRepository.existsByLocation(location);
    }

    @Override
    public HotelDto updateHotel(String hotelCode, HotelPatchDto hotelPatchDto) {
        Hotel hotel = hotelRepository.findById(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with code: " + hotelCode));
        hotelPatchDto.getName().ifPresent(hotel::setName);
        hotelPatchDto.getLocation().ifPresent(hotel::setLocation);
        return modelMapper.map(hotelRepository.save(hotel), HotelDto.class);
    }

    @Override
    public List<HotelDto> getHotels(LocalDate dateFrom, LocalDate dateTo, String destination) {
        List<Hotel> hotels;

        if (dateFrom != null && dateTo != null) {
            hotels = hotelRepository.findAvailableHotelsByDateRangeAndDestination(dateFrom, dateTo, destination);
        } else {
            hotels = (destination != null) ? hotelRepository.findByLocation(destination) : hotelRepository.findAll();
        }

        return hotels.stream()
                .map(hotel -> {
                    HotelDto dto = modelMapper.map(hotel, HotelDto.class);
                    int totalRooms = (int) hotel.getRooms().stream().filter(room -> !room.getIsDeleted()).count();
                    int availableRooms = (int) hotel.getRooms().stream()
                            .filter(room -> !room.getIsDeleted() && room.getIsAvailable())
                            .count();
                    dto.setNumberOfRooms(totalRooms);
                    return dto;
                }).collect(Collectors.toList());
    }

    @Override
    public HotelDto getHotelDetails(String hotelCode) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode)
                .orElseThrow(() -> new HotelNotFoundException("Hotel not found with code: " + hotelCode));

        int numberOfRooms = (int) hotel.getRooms().stream().filter(room -> !room.getIsDeleted()).count();

        HotelDto hotelDetailDTO = modelMapper.map(hotel, HotelDto.class);
        hotelDetailDTO.setNumberOfRooms(numberOfRooms);

        return hotelDetailDTO;
    }
}
