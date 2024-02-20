package com.MoralesValverdeGerman.pruebatec4.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Passenger {
    @Id
    private String dni;
    private String name;
    private String surName;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private FlightBooking booking; // La reserva a la que pertenece el pasajero
}
