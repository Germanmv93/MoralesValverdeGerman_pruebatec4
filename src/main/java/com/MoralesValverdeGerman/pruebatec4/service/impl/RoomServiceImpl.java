package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.exception.RoomNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.RoomRepository;
import com.MoralesValverdeGerman.pruebatec4.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository, ModelMapper modelMapper) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Optional<Hotel> hotel = hotelRepository.findByHotelCode(roomDto.getHotelCode());
        hotel.orElseThrow(() -> new HotelNotFoundException("Hotel not found with code: " + roomDto.getHotelCode()));

        roomRepository.findByRoomNumberAndHotel_HotelCode(roomDto.getRoomNumber(), roomDto.getHotelCode())
                .ifPresent(r -> {
                    throw new RoomNotFoundException("Room with number " + r.getRoomNumber() + " already exists in hotel " + r.getHotel().getHotelCode() + ".");
                });
        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel.get());
        setRoomCapacityBasedOnType(room);
        Room savedRoom = roomRepository.save(room);
        return modelMapper.map(savedRoom, RoomDto.class);
    }
    @Override
    public List<RoomDto> findAllRooms() {
        return roomRepository.findAll().stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<RoomDto> findRoomById(Long id) {
        return roomRepository.findById(id)
                .map(room -> modelMapper.map(room, RoomDto.class));
    }
    @Override
    public Optional<RoomDto> findByRoomNumberAndHotel_HotelCode(String roomNumber, String hotelCode) {
        return roomRepository.findByRoomNumberAndHotel_HotelCode(roomNumber, hotelCode)
                .map(room -> modelMapper.map(room, RoomDto.class));
    }
    @Override
    public void deleteRoomFromHotel(String roomNumber, String hotelCode) {
        Room room = roomRepository.findByRoomNumberAndHotel_HotelCode(roomNumber, hotelCode)
                .orElseThrow(() -> new RoomNotFoundException("Sorry, room with number " + roomNumber + " not found in hotel " + hotelCode));
        room.setIsDeleted(true);
        room.setIsAvailable(false);
        roomRepository.save(room);
    }
    @Override
    public void reactivateRoom(String roomNumber, String hotelCode) {
        Room room = roomRepository.findByRoomNumberAndHotel_HotelCode(roomNumber, hotelCode)
                .orElseThrow(() -> new RoomNotFoundException("Room with number " + roomNumber + " not found in hotel " + hotelCode));
        room.setIsDeleted(false);
        room.setIsAvailable(true);
        roomRepository.save(room);
    }

    private void setRoomCapacityBasedOnType(Room room) {
        switch (room.getRoomType()) {
            case "Individual":
                room.setCapacity(1);
                break;
            case "Double":
                room.setCapacity(2);
                break;
            case "Triple":
                room.setCapacity(3);
                break;
            default:
                throw new RoomNotFoundException("Unknown room type: " + room.getRoomType() + ".\n The allowed room types are:\n Individual.\n Double.\n Triple.");
        }
    }
}
