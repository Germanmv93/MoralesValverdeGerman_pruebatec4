package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;

import com.MoralesValverdeGerman.pruebatec4.exception.InsufficientRoomCapacityException;
import com.MoralesValverdeGerman.pruebatec4.exception.NoAvailableRoomException;
import com.MoralesValverdeGerman.pruebatec4.exception.RoomNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.RoomRepository;
import com.MoralesValverdeGerman.pruebatec4.service.BookingHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings Hotels", description = "Booking management API")
public class BookingHotelController {
    @Autowired
    private BookingHotelService bookingHotelService;
    @Autowired
    private RoomRepository roomRepository;
    @PostMapping
    @Operation(summary = "Create a booking", description = "Create a new booking with the given details")
    public ResponseEntity<BookingHotelDto> createBooking(@RequestBody BookingHotelDto bookingDto) {
        BookingHotelDto createdBookingDto = bookingHotelService.createBooking(bookingDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookingDto);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a booking", description = "Delete a booking with the specified ID")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingHotelService.deleteBooking(id);
        return ResponseEntity.ok("Reservation cancelled and the room is now available.");
    }
    @GetMapping
    @Operation(summary = "List all bookings", description = "Get a list of all bookings")
    public ResponseEntity<List<BookingHotelDto>> getAllBookings() {
        List<BookingHotelDto> bookingDtos = bookingHotelService.getAllBookingsDto();
        return ResponseEntity.ok(bookingDtos);
    }


}

