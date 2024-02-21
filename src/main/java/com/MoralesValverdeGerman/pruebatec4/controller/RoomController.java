package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.service.RoomService;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Room Management", description = "API for managing rooms in the hotel booking system")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private HotelService hotelService;

    // Get all rooms
    @GetMapping
    @Operation(summary = "Get all rooms", description = "Returns a list of all rooms in the system")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        List<RoomDto> rooms = roomService.findAllRooms();
        return ResponseEntity.ok(rooms);
    }
    @PostMapping("/new")
    @Operation(summary = "Create a new room", description = "Creates a new room with the provided details")
    public ResponseEntity<RoomDto> createRoom(@Valid @RequestBody RoomDto roomDto) {
        RoomDto savedRoom = roomService.createRoom(roomDto);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }
    @DeleteMapping("/{roomNumber}/hotel/{hotelCode}")
    @Operation(summary = "Delete a room from a hotel", description = "Deletes a room from a specified hotel by room number and hotel code")
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
    @Operation(summary = "Reactivate a room", description = "Reactivates a previously deactivated room in a specified hotel")
    public ResponseEntity<String> reactivateRoom(@PathVariable String roomNumber, @PathVariable String hotelCode) {
        roomService.reactivateRoom(roomNumber, hotelCode);
        return ResponseEntity.ok("Room number " + roomNumber + " in hotel " + hotelCode + " has been successfully reactivated.");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID", description = "Returns the details of a room by its ID")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        return roomService.findRoomById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}