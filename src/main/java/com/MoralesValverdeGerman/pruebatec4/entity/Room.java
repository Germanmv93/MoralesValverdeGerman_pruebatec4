package com.MoralesValverdeGerman.pruebatec4.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente el ID.
    private Long id; // ID único para cada habitación.

    private String roomNumber; // Número de la habitación, ya no es único globalmente.

    private Double pricePerNight; // Precio por noche de la habitación.

    @ManyToOne(fetch = FetchType.LAZY) // Relación Muchos a Uno con Hotel.
    @JoinColumn(name = "hotel_hotelCode", nullable = false) // Clave foránea que referencia a Hotel.
    private Hotel hotel; // El hotel al que pertenece esta habitación.

    private String roomType;
    private Boolean isAvailable;
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "room")
    private Set<BookingHotel> bookings;
}
