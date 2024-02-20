package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;

import com.MoralesValverdeGerman.pruebatec4.exception.InsufficientRoomCapacityException;
import com.MoralesValverdeGerman.pruebatec4.exception.NoAvailableRoomException;
import com.MoralesValverdeGerman.pruebatec4.exception.RoomNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.RoomRepository;
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
    @Autowired
    private RoomRepository roomRepository;
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingHotelDto bookingDto) {
        // Llama al servicio para crear la reserva, pasando el DTO recibido en la solicitud
        BookingHotel createdBooking = bookingHotelService.createBooking(bookingDto);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Formatea las fechas de check-in y check-out para incluirlas en el mensaje de respuesta
        String formattedCheckIn = createdBooking.getCheckIn().format(formatter);
        String formattedCheckOut = createdBooking.getCheckOut().format(formatter);
        String roomNumber = createdBooking.getRoom().getRoomNumber();
        // Construye el mensaje de respuesta utilizando la información de la reserva creada
        String message = String.format("Your booking with ID %d has been successfully created for room number %s with check-in on %s and check-out on %s. The total price of your booking is %.2f.",
                createdBooking.getId(), roomNumber, formattedCheckIn, formattedCheckOut, createdBooking.getTotalPrice());

        // Devuelve una respuesta con el mensaje de éxito y el código de estado HTTP CREATED
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

