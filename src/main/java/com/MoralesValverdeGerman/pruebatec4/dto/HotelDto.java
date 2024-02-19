package com.MoralesValverdeGerman.pruebatec4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private String hotelCode;
    private String name;
    private String location;
    private int numberOfRooms;
    private int availableRooms;
}
