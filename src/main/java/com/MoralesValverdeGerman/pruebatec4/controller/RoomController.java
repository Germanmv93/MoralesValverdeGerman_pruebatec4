package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.service.RoomService;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import com.MoralesValverdeGerman.pruebatec4.utils.RoomUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/agency/rooms")
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
    @PostMapping("/new")
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        RoomDto savedRoom = roomService.createRoom(roomDto);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }
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

    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return roomService.findRoomById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}