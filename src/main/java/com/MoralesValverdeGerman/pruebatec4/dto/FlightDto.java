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
public class FlightDto {
    private String flightCode;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private String origin;
    private String destination;
    private int numberOfPersons;
    private String seatType;
}

