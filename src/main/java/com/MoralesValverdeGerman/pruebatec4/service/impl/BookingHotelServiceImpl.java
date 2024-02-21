package com.MoralesValverdeGerman.pruebatec4.service.impl;

import com.MoralesValverdeGerman.pruebatec4.dto.BookingHotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.RoomDto;
import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.exception.*;
import com.MoralesValverdeGerman.pruebatec4.repository.BookingHotelRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.RoomRepository;
import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.service.BookingHotelService;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class BookingHotelServiceImpl implements BookingHotelService {

    @Autowired
    private BookingHotelRepository bookingHotelRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingHotelDto createBooking(BookingHotelDto bookingDto) {
        // Validar ubicación del hotel
        Hotel hotel = hotelRepository.findByHotelCode(bookingDto.getHotelCode())
                .orElseThrow(() -> new HotelNotFoundException("Hotel with code " + bookingDto.getHotelCode() + " not found"));

        if (!hotel.getLocation().equalsIgnoreCase(bookingDto.getLocation())) {
            throw new LocationMismatchException("Sorry, the hotel's location does not match the one registered in the database for this hotel.");
        }

        // Validar número de noches
        long daysBetween = ChronoUnit.DAYS.between(bookingDto.getCheckIn(), bookingDto.getCheckOut());
        long correctNights = ChronoUnit.DAYS.between(bookingDto.getCheckIn(), bookingDto.getCheckOut());
        if (daysBetween != bookingDto.getNights()) {
            throw new InvalidDateException(String.format("The number of nights does not match the dates provided. (Check-in/Check-Out). The correct number of nights is %d.", correctNights));
        }

        Set<String> allowedRoomTypes = Set.of("Individual", "Double", "Triple");
        if (!allowedRoomTypes.contains(bookingDto.getRoomType())) {
            throw new RoomNotFoundException("Unknown room type: " + bookingDto.getRoomType() + ".\n The allowed room types are:\n Individual.\n Double.\n Triple.");
        }

        // Verificar disponibilidad de habitaciones
        List<Room> availableRooms = roomRepository.findByHotel_HotelCodeAndHotel_LocationAndRoomTypeAndIsAvailable(
                bookingDto.getHotelCode(), bookingDto.getLocation(), bookingDto.getRoomType(), true);

        if (availableRooms.isEmpty()) {
            throw new NoAvailableRoomException("Sorry, there are no rooms available.");
        }

        // Seleccionar una habitación aleatoria
        Room selectedRoom = availableRooms.get(new Random().nextInt(availableRooms.size()));

        // Verificar capacidad
        if (bookingDto.getNumberOfGuest() > selectedRoom.getCapacity()) {
            throw new InsufficientRoomCapacityException("The selected room does not have enough capacity for the number of guests.");
        }

        selectedRoom.setIsAvailable(false); // Cambiar isAvailable a false
        roomRepository.save(selectedRoom); // Guardar

        double pricePerNight = selectedRoom.getPricePerNight(); // Asume que este método/propiedad existe
        double totalPrice = pricePerNight * bookingDto.getNights();

        // Crear y guardar la reserva
        BookingHotel booking = modelMapper.map(bookingDto, BookingHotel.class);
        booking.setRoom(selectedRoom); // Asignar la habitación seleccionada a la reserva
        booking.setTotalPrice(totalPrice); // Establecer el precio total calculado
        BookingHotel savedBooking = bookingHotelRepository.save(booking);

        // Mapear a BookingHotelDto para devolver, incluyendo el precio total
        BookingHotelDto resultDto = modelMapper.map(savedBooking, BookingHotelDto.class);
        resultDto.setTotalPrice(totalPrice); // Asegurarse de que el precio total se incluya en el DTO

        return resultDto;
    }

    public String deleteBooking(Long bookingId) {
        BookingHotel booking = bookingHotelRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Sorry, reservation not found. Please check the details and try again."));

        Room room = booking.getRoom();
        room.setIsAvailable(true); // Asume que tienes un setter para cambiar el estado
        roomRepository.save(room);

        bookingHotelRepository.delete(booking);

        return "Reservation cancelled and the room is now available.";
    }

    @Override
    public List<BookingHotelDto> getAllBookingsDto() {
        List<BookingHotel> bookings = bookingHotelRepository.findAll();
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingHotelDto.class))
                .collect(Collectors.toList());
    }
}


