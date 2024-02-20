package com.MoralesValverdeGerman.pruebatec4.utils;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;

public class RoomUtils {
    public static RoomDto convertToRoomDto(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setRoomType(room.getRoomType());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setIsAvailable(room.getIsAvailable());
        dto.setIsDeleted(room.getIsDeleted());
        if (room.getHotel() != null) {
            // Asumiendo que Room tiene una relación con Hotel y que Hotel tiene un código o identificador.
            dto.setHotelCode(room.getHotel().getHotelCode());
        }
        return dto;
    }

    public static Room convertToRoom(RoomDto roomDto, Hotel hotel) {
        Room room = new Room();
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setPricePerNight(roomDto.getPricePerNight());
        room.setRoomType(roomDto.getRoomType());
        room.setIsAvailable(roomDto.getIsAvailable());
        room.setHotel(hotel); // Establece el hotel al que pertenece esta habitación
        return room;
    }
}
