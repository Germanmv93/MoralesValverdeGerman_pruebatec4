package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.InsufficientRoomCapacityException;
import com.MoralesValverdeGerman.pruebatec4.exception.RoomNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.service.BookingHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingHotelController {
    @Autowired
    private BookingHotelService bookingHotelService;
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingHotelDto bookingDto) {
        BookingHotel createdBooking = bookingHotelService.createBooking(bookingDto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formattedCheckIn = createdBooking.getCheckIn().format(formatter);
        String formattedCheckOut = createdBooking.getCheckOut().format(formatter);

        Room room = roomRepository.findById(bookingDto.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));

        if (bookingDto.getNumberOfGuest() > room.getCapacity()) {
            throw new InsufficientRoomCapacityException("La habitación seleccionada no tiene suficiente capacidad para el número de huéspedes.");
        }

        String message = String.format("Your booking with ID %d has been successfully created with check-in on %s and check-out on %s. The total price of your booking is %.2f.",
                createdBooking.getId(), formattedCheckIn, formattedCheckOut, createdBooking.getTotalPrice());
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) {
        bookingHotelService.deleteBooking(id);
        return ResponseEntity.ok("Reservation cancelled and the room is now available.");
    }
    @GetMapping
    public ResponseEntity<List<BookingHotelDto>> getAllBookings() {
        List<BookingHotelDto> bookingDtos = bookingHotelService.getAllBookingsDto();
        return ResponseEntity.ok(bookingDtos);
    }
}

