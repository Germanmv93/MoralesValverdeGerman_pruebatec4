package com.MoralesValverdeGerman.pruebatec4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    private Long id; // ID único
    private String roomNumber;
    private Double pricePerNight;
    private String roomType;
    private Boolean isAvailable;
    private Boolean isDeleted;
    private String hotelCode;
}

