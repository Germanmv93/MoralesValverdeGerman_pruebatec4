package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.RoomNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.RoomRepository;
import com.MoralesValverdeGerman.pruebatec4.service.RoomService;
import com.MoralesValverdeGerman.pruebatec4.utils.RoomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<RoomDto> findAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(RoomUtils::convertToRoomDto).collect(Collectors.toList());
    }

    @Override
    public Optional<Room> findRoomByNumber(String roomNumber) {
        return roomRepository.findByRoomNumber(roomNumber);
    }

//    @Override
//    public Room findRoomByNumber(String roomNumber) {
//        return roomRepository.findByRoomNumber(roomNumber)
//                .orElseThrow(() -> new RoomNotFoundException(roomNumber));
//    }


    @Override
    public Room saveRoom(Room room) {
        assignRoomCapacityBasedOnType(room);
        return roomRepository.save(room);
    }

    @Override
    public void deleteRoomFromHotel(String roomNumber, String hotelCode) {
        Optional<Room> roomOptional = roomRepository.findByRoomNumberAndHotel_HotelCode(roomNumber, hotelCode);
        if (!roomOptional.isPresent()) {
            throw new RoomNotFoundException("Sorry, room with number " + roomNumber + " not found in hotel " + hotelCode);
        }
        Room room = roomOptional.get();
        room.setIsDeleted(true); // Asume que has añadido un campo isDeleted a tu entidad Room
        room.setIsAvailable(false);
        roomRepository.save(room); // Actualiza el registro en lugar de eliminarlo
    }

    @Override
    public Optional<Room> findByRoomNumberAndHotel_HotelCode(String roomNumber, String hotelCode) {
        return roomRepository.findByRoomNumberAndHotel_HotelCode(roomNumber, hotelCode);
    }

    @Override
    public void reactivateRoom(String roomNumber, String hotelCode) {
        Optional<Room> roomOptional = roomRepository.findByRoomNumberAndHotel_HotelCode(roomNumber, hotelCode);
        if (!roomOptional.isPresent()) {
            throw new RoomNotFoundException("Room with number " + roomNumber + " not found in hotel " + hotelCode);
        }
        Room room = roomOptional.get();
        room.setIsDeleted(false); // Cambiar el estado a no borrado
        room.setIsAvailable(true);
        roomRepository.save(room); // Guardar el cambio en la base de datos
    }

    private void assignRoomCapacityBasedOnType(Room room) {
        switch (room.getRoomType()) {
            case "Individual":
                room.setCapacity(1);
                break;
            case "Doble":
                room.setCapacity(2);
                break;
            case "Triple":
                room.setCapacity(3);
                break;
            default:
                throw new IllegalArgumentException("Tipo de habitación desconocido o no soportado: " + room.getRoomType());
        }
    }
}