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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomNumber;

    private Double pricePerNight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_hotelCode", nullable = false)
    private Hotel hotel;

    private String roomType;
    private int capacity;
    private Boolean isAvailable;
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "room")
    private Set<BookingHotel> bookings;
}
