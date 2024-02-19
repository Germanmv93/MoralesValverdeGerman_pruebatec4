package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import com.MoralesValverdeGerman.pruebatec4.utils.HotelUtils;
import com.MoralesValverdeGerman.pruebatec4.exception.LocationMismatchException;
import com.MoralesValverdeGerman.pruebatec4.exception.NoAvailableHotelsException;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    public ResponseEntity<List<HotelDto>> getHotels(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
            @RequestParam(required = false) String destination) {
        List<HotelDto> hotelDTOs;
        if (destination != null && !hotelService.existsByLocation(destination)) {
            throw new LocationMismatchException("The provided location does not exist in the database.");
        }
        if (dateFrom != null && dateTo != null && destination != null) {
            hotelDTOs = hotelService.getAvailableHotels(dateFrom, dateTo, destination);
            if (hotelDTOs.isEmpty()) {
                throw new NoAvailableHotelsException("Sorry, no availability for the provided dates and location.");
            }
        } else {
            List<Hotel> hotels = hotelService.findAllHotels();
            hotelDTOs = hotels.stream()
                    .map(HotelUtils::convertToHotelDto) // Utiliza el método convertToHotelDto de la clase de utilidad
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(hotelDTOs);
    }
    @GetMapping("/hotels/{hotelCode}")
    public ResponseEntity<HotelDto> getHotelByCode(@PathVariable String hotelCode) {
        Hotel hotel = hotelService.findHotelByCode(hotelCode);
        int numberOfRooms = hotel.getRooms().size();
        int availableRooms = (int) hotel.getRooms().stream().filter(Room::getIsAvailable).count();

        HotelDto hotelDetailDTO = new HotelDto();
        hotelDetailDTO.setHotelCode(hotel.getHotelCode());
        hotelDetailDTO.setName(hotel.getName());
        hotelDetailDTO.setLocation(hotel.getLocation());
        hotelDetailDTO.setNumberOfRooms(numberOfRooms);
        hotelDetailDTO.setAvailableRooms(availableRooms);

        return ResponseEntity.ok(hotelDetailDTO);
    }

    // Create a new hotel
    @PostMapping("/hotels/new")
    public ResponseEntity<String> createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelService.saveHotel(hotel);
        if (savedHotel != null) {
            String responseMessage = String.format(
                    "El hotel con id %s ha sido creado. Sus características son:\n Nombre: %s.\n Localización: %s.",
                    savedHotel.getHotelCode(), savedHotel.getName(), savedHotel.getLocation());
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.badRequest().body("No se pudo crear el hotel");
        }
    }

    // Update an existing hotel
    @PutMapping("/{hotelCode}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable String hotelCode, @RequestBody Hotel hotelDetails) {
        Hotel hotel = hotelService.findHotelByCode(hotelCode);
        hotel.setName(hotelDetails.getName());
        hotel.setLocation(hotelDetails.getLocation());
        // Update other fields as necessary
        final Hotel updatedHotel = hotelService.saveHotel(hotel);
        return ResponseEntity.ok(updatedHotel);
    }

    // Delete a hotel
    @DeleteMapping("/hotels/delete/{hotelCode}")
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelCode) {
        hotelService.deleteHotel(hotelCode);
        String message = String.format("The hotel %s has been successfully deleted from the database.", hotelCode);
        return ResponseEntity.ok(message);
    }

//    @GetMapping("/hotels/list")
//    public ResponseEntity<List<HotelDto>> getAvailableHotels(
//            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
//            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
//            @RequestParam String destination) {
//
//        List<HotelDto> availableHotelsDto = hotelService.getAvailableHotels(dateFrom, dateTo, destination);
//
//        if (availableHotelsDto.isEmpty()) {
//            throw new NoAvailableHotelsException("Sorry, no availability for the provided dates and location.");
//        }
//
//        return ResponseEntity.ok(availableHotelsDto);
//    }
}