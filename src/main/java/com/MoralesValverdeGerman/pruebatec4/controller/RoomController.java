package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.service.RoomService;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;

    // Get all rooms
    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<RoomDto> rooms = roomService.findAllRooms();
        return ResponseEntity.ok(rooms);
    }

    // Get a single room by number
//    @GetMapping("/{roomNumber}")
//    public ResponseEntity<Optional<Room>> getRoomByNumber(@PathVariable String roomNumber) {
//        Optional<Room> room = roomService.findRoomByNumber(roomNumber);
//        return ResponseEntity.ok(room);
//    }

    // Create a new room
    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody Room room) {
        Hotel hotel = hotelService.findHotelByCode(room.getHotel().getHotelCode());
        if (hotel == null) {
            return ResponseEntity.badRequest().body("El hotel especificado no existe.");
        }

        Optional<Room> existingRoom = roomService.findByRoomNumberAndHotel_HotelCode(room.getRoomNumber(), hotel.getHotelCode());
        if (existingRoom.isPresent()) {
            return ResponseEntity.badRequest().body("Room with number " + room.getRoomNumber() + " already exists in hotel " + hotel.getHotelCode() + ".");
        }

        room.setHotel(hotel);
        Room savedRoom = roomService.saveRoom(room);

        String message = String.format("Room No. %s has been successfully added to Hotel %s.", savedRoom.getRoomNumber(), hotel.getHotelCode());
        return ResponseEntity.ok(message);
    }

    // Update an existing room
//    @PutMapping("/{roomNumber}")
//    public ResponseEntity<Room> updateRoom(@PathVariable String roomNumber, @RequestBody Room roomDetails) {
//        Room room = roomService.findRoomByNumber(roomNumber);
//        room.setRoomType(roomDetails.getRoomType());
//        room.setPricePerNight(roomDetails.getPricePerNight());
//        room.setIsAvailable(roomDetails.getIsAvailable());
//        // Update other fields as necessary
//        final Room updatedRoom = roomService.saveRoom(room);
//        return ResponseEntity.ok(updatedRoom);
//    }

    // Delete a room
    @DeleteMapping("/{roomNumber}/hotel/{hotelCode}")
    public ResponseEntity<String> deleteRoomFromHotel(@PathVariable String roomNumber, @PathVariable String hotelCode) {
        try {
            roomService.deleteRoomFromHotel(roomNumber, hotelCode);
            String message = String.format("Room number %s successfully deleted from hotel %s.", roomNumber, hotelCode);
            return ResponseEntity.ok(message);
        } catch (RuntimeException ex) {
            // Asumiendo que capturas una RuntimeException o una excepción más específica que creaste.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PatchMapping("/{roomNumber}/hotel/{hotelCode}/reactivate")
    public ResponseEntity<String> reactivateRoom(@PathVariable String roomNumber, @PathVariable String hotelCode) {
        roomService.reactivateRoom(roomNumber, hotelCode);
        return ResponseEntity.ok("Room number " + roomNumber + " in hotel " + hotelCode + " has been successfully reactivated.");
    }




}