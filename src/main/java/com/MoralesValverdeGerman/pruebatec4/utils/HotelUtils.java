package com.MoralesValverdeGerman.pruebatec4.utils;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;

import java.util.List;
import java.util.stream.Collectors;

public class HotelUtils {
    public static HotelDto convertToHotelDto(Hotel hotel) {
        HotelDto dto = new HotelDto();
        dto.setHotelCode(hotel.getHotelCode());
        dto.setName(hotel.getName());
        dto.setLocation(hotel.getLocation());

        // Considera todas las habitaciones para el total, no solo las activas y disponibles
        int totalRooms = hotel.getRooms().size();

        // Filtra las habitaciones activas y disponibles para calcular availableRooms
        List<Room> activeRooms = hotel.getRooms().stream()
                .filter(room -> !room.getIsDeleted())
                .collect(Collectors.toList());
        int availableRooms = (int) activeRooms.stream()
                .filter(Room::getIsAvailable)
                .count();

        dto.setNumberOfRooms(totalRooms); // Refleja el total de habitaciones, sin importar su estado
        dto.setAvailableRooms(availableRooms); // Refleja solo las habitaciones disponibles y no borradas

        return dto;
    }
}
