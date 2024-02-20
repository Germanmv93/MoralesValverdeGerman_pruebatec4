package com.MoralesValverdeGerman.pruebatec4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelPatchDto {
    private Optional<String> name = Optional.empty();
    private Optional<String> location = Optional.empty();
}
