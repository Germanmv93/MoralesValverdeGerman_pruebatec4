package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.*;
import com.MoralesValverdeGerman.pruebatec4.repository.BookingHotelRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.RoomRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.service.BookingHotelService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class BookingHotelServiceImpl implements BookingHotelService {

    @Autowired
    private BookingHotelRepository bookingHotelRepository;

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public BookingHotel createBooking(BookingHotelDto bookingDto) {
        Optional<Hotel> hotelOptional = hotelRepository.findByHotelCode(bookingDto.getHotelCode());

        Hotel hotel = hotelOptional.orElseThrow(() -> new BookingHotelNotFoundException("Sorry, the hotel with ID " + bookingDto.getHotelCode() + " does not exist."));

        if (!hotel.getLocation().equalsIgnoreCase(bookingDto.getLocation())) {
            throw new LocationMismatchException("Sorry, the hotel's location does not match the one registered in the database for this hotel.");
        }

        long daysBetween = ChronoUnit.DAYS.between(bookingDto.getCheckIn(), bookingDto.getCheckOut());
        if (daysBetween != bookingDto.getNights()) {
            throw new InvalidDateException("The number of nights does not match the dates provided. (Check-in/Check-Out)");
        }

        List<Room> availableRooms = roomRepository.findByHotel_HotelCodeAndHotel_LocationAndRoomTypeAndIsAvailable(
                bookingDto.getHotelCode(), bookingDto.getLocation(), bookingDto.getRoomType(), true);

        if (availableRooms.isEmpty()) {
            throw new NoAvailableRoomException("Sorry, there are no rooms available.");
        }

        // Por simplicidad, elegimos la primera habitación disponible
        Room selectedRoom = availableRooms.get(0);
        BookingHotel booking = new BookingHotel();
        booking.setRoom(selectedRoom);
        booking.setCheckIn(bookingDto.getCheckIn());
        booking.setCheckOut(bookingDto.getCheckOut());
        booking.setHotelCode(bookingDto.getHotelCode());
        booking.setLocation(bookingDto.getLocation());
        booking.setNights(bookingDto.getNights());
        booking.setNumberOfGuest(bookingDto.getNumberOfGuest());

        // Calcular el precio total de la reserva
        double totalPrice = selectedRoom.getPricePerNight() * bookingDto.getNights();
        booking.setTotalPrice(totalPrice); // Asegúrate de que BookingHotel tenga un atributo para almacenar el precio total

        // Aquí, podrías marcar la habitación como no disponible si es necesario
        selectedRoom.setIsAvailable(false);
        roomRepository.save(selectedRoom);

        return bookingHotelRepository.save(booking);
    }

    public String deleteBooking(Long bookingId) {
        BookingHotel booking = bookingHotelRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reservation not found. Please check the details and try again."));

        Room room = booking.getRoom();
        room.setIsAvailable(true); // Asume que tienes un setter para cambiar el estado
        roomRepository.save(room);

        bookingHotelRepository.delete(booking);

        return "Reservation cancelled and the room is now available.";
    }

    @Override
    public List<BookingHotelDto> getAllBookingsDto() {
        List<BookingHotel> bookings = bookingHotelRepository.findAll();
        // Convierte la lista de entidades a una lista de DTOs
        List<BookingHotelDto> bookingDtos = bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return bookingDtos;
    }

    private BookingHotelDto convertToDto(BookingHotel booking) {
        BookingHotelDto dto = new BookingHotelDto();
        dto.setId(booking.getId());
        dto.setCheckIn(booking.getCheckIn());
        dto.setCheckOut(booking.getCheckOut());
        dto.setLocation(booking.getLocation());
        dto.setHotelCode(booking.getHotelCode());
        dto.setNights(booking.getNights());
        dto.setNumberOfGuest(booking.getNumberOfGuest());
        dto.setRoomType(booking.getRoom().getRoomType());
        // Asegúrate de que los métodos get en tu entidad BookingHotel estén correctamente implementados.
        return dto;
    }



}


