package com.MoralesValverdeGerman.pruebatec4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightBookingDto {
    private LocalDate date;
    private String origin;
    private String destination;
    private String flightCode;
    private int numberOfPersons; // Cantidad de personas ajustada de peopleQ a numberOfPersons
    private String seatType; // Tipo de asiento, por ejemplo, Economy
    private List<PassengerDto> passengers; // Lista de pasajeros
}
