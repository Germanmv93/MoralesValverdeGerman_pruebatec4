package com.MoralesValverdeGerman.pruebatec4.controller;

import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.dto.HotelPatchDto;
import com.MoralesValverdeGerman.pruebatec4.exception.LocationMismatchException;
import com.MoralesValverdeGerman.pruebatec4.exception.NoAvailableHotelsException;
import com.MoralesValverdeGerman.pruebatec4.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/agency")
@Tag(name = "Hotel Management", description = "API for managing hotels in the booking system")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotels")
    @Operation(summary = "Get hotels", description = "Returns a list of hotels optionally filtered by date range and destination",
            responses = {
                    @ApiResponse(description = "Successful operation", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = HotelDto.class))),
                    @ApiResponse(description = "Location mismatch exception", responseCode = "400"),
                    @ApiResponse(description = "No available hotels exception", responseCode = "404")
            })
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
    @Operation(summary = "Get hotel by code", description = "Returns details of a hotel by its code",
            responses = @ApiResponse(description = "Successful operation", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HotelDto.class))))
    public ResponseEntity<HotelDto> getHotelByCode(@PathVariable String hotelCode) {
        HotelDto hotelDetailDTO = hotelService.getHotelDetails(hotelCode);
        return ResponseEntity.ok(hotelDetailDTO);
    }

    @PostMapping("/hotels/new")
    @Operation(summary = "Create a hotel", description = "Creates a new hotel with the provided details",
            responses = @ApiResponse(description = "Hotel created successfully", responseCode = "200"))
    public ResponseEntity<String> createHotel(@Validated @RequestBody HotelDto hotelDto) {
        hotelService.createHotel(hotelDto);
        return ResponseEntity.ok("Hotel created successfully.");
    }

    @PatchMapping("/hotels/{hotelCode}")
    @Operation(summary = "Update a hotel", description = "Updates details of an existing hotel",
            responses = @ApiResponse(description = "Successful operation", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HotelDto.class))))
    public ResponseEntity<HotelDto> updateHotel(@PathVariable String hotelCode, @RequestBody HotelPatchDto hotelPatchDto) {
        HotelDto updatedHotel = hotelService.updateHotel(hotelCode, hotelPatchDto);
        return ResponseEntity.ok(updatedHotel);
    }

    @DeleteMapping("/hotels/delete/{hotelCode}")
    @Operation(summary = "Delete a hotel", description = "Deletes a hotel by its code",
            responses = @ApiResponse(description = "Hotel deleted successfully", responseCode = "200"))
    public ResponseEntity<String> deleteHotel(@PathVariable String hotelCode) {
        hotelService.deleteHotel(hotelCode);
        return ResponseEntity.ok(String.format("The hotel %s has been successfully deleted.", hotelCode));
    }
}
