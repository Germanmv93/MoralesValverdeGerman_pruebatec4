package com.MoralesValverdeGerman.pruebatec4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingHotelDto {

    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate checkIn;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate checkOut;
    private String location;
    private String hotelCode;
    private int nights;
    private int numberOfGuest;
    private Double totalPrice;
    private String roomType;
    private String roomNumber;

}
