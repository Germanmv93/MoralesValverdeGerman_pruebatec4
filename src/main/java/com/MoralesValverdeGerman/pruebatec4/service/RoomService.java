package com.MoralesValverdeGerman.pruebatec4.service;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    List<RoomDto> findAllRooms();
//    Room findRoomByNumber(String roomNumber);

    Optional<Room> findRoomByNumber(String roomNumber);

    Room saveRoom(Room room);
    void deleteRoomFromHotel(String roomNumber, String hotelCode);

    void reactivateRoom(String roomNumber, String hotelCode);


    Optional<Room> findByRoomNumberAndHotel_HotelCode(String roomNumber, String hotelCode);




}


