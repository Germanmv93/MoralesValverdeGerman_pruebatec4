package com.MoralesValverdeGerman.pruebatec4.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    @NotBlank(message = "Hotel code cannot be empty")
    private String hotelCode;
    @NotBlank(message = "Hotel code cannot be empty")
    private String name;
    @NotBlank(message = "Hotel code cannot be empty")
    private String location;
    private int numberOfRooms;
}
