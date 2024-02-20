package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.HotelPatchDto;
import com.MoralesValverdeGerman.pruebatec4.exception.LocationMismatchException;
import com.MoralesValverdeGerman.pruebatec4.exception.NoAvailableHotelsException;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
        // Verifica si el destino existe en la base de datos
        if (destination != null && !hotelService.existsByLocation(destination)) {
            throw new LocationMismatchException("The provided location does not exist in the database.");
        }
        List<HotelDto> hotelDTOs = hotelService.getHotels(dateFrom, dateTo, destination);
        if (hotelDTOs.isEmpty()) {
            throw new NoAvailableHotelsException("Sorry, no availability for the provided dates and location.");
        }
        return ResponseEntity.ok(hotelDTOs);
    }


    @GetMapping("/hotels/{hotelCode}")
    public ResponseEntity<HotelDto> getHotelByCode(@PathVariable String hotelCode) {
        HotelDto hotelDetailDTO = hotelService.getHotelDetails(hotelCode);
        return ResponseEntity.ok(hotelDetailDTO);
    }

    @PostMapping("/hotels/new")
    public ResponseEntity<String> createHotel(@Validated @RequestBody HotelDto hotelDto) {
        hotelService.createHotel(hotelDto);
        return ResponseEntity.ok("Hotel created successfully.");
    }

    @PatchMapping("/hotels/{hotelCode}")
    public ResponseEntity<HotelDto> updateHotel(@PathVariable String hotelCode, @RequestBody HotelPatchDto hotelPatchDto) {
        HotelDto updatedHotel = hotelService.updateHotel(hotelCode, hotelPatchDto);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/hotels/delete/{hotelCode}")
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelCode) {
        hotelService.deleteHotel(hotelCode);
        return ResponseEntity.ok(String.format("The hotel %s has been successfully deleted.", hotelCode));
    }
//    @GetMapping("/hotels/available")
//    public ResponseEntity<List<HotelDto>> getHotelsWithAvailability(
//            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateFrom,
//            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateTo,
//            @RequestParam(required = false) String destination) {
//        List<HotelDto> hotelDTOs = hotelService.getHotelsWithAvailability(dateFrom, dateTo, destination);
//        // No es necesario verificar si hotelDTOs está vacío aquí si getHotelsWithAvailability ya lanza la excepción
//        return ResponseEntity.ok(hotelDTOs);
//    }
}
