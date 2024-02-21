package com.MoralesValverdeGerman.pruebatec4.repository;

import com.MoralesValverdeGerman.pruebatec4.entity.BookingHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface BookingHotelRepository extends JpaRepository<BookingHotel, Long> {
    @Query("SELECT COUNT(b) > 0 FROM BookingHotel b WHERE b.room.roomNumber = :roomNumber AND NOT (b.checkOut <= :checkIn OR b.checkIn >= :checkOut)")
    boolean existsBookingByRoomAndDateRange(@Param("roomNumber") String roomNumber, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);
}

