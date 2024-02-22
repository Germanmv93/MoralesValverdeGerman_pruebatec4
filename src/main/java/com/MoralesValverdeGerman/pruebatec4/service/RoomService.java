package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    RoomDto createRoom(RoomDto roomDto);
    Optional<RoomDto> findByRoomNumberAndHotel_HotelCode(String roomNumber, String hotelCode);
    List<RoomDto> findAllRooms();
    void deleteRoomFromHotel(String roomNumber, String hotelCode);
    void reactivateRoom(String roomNumber, String hotelCode);
    Optional<RoomDto> findRoomById(Long id);

}
