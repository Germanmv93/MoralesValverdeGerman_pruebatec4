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

    public static Room convertToRoom(RoomDto dto, Hotel hotel) {
        Room room = new Room();
        room.setId(dto.getId()); // Asume que el ID es manejado automáticamente si es null
        room.setRoomNumber(dto.getRoomNumber());
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPricePerNight());
        room.setIsAvailable(dto.getIsAvailable());
        room.setIsDeleted(dto.getIsDeleted());
        room.setHotel(hotel); // Asume que el hotel ya ha sido validado y cargado
        return room;
    }
}
