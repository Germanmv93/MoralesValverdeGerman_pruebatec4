package com.MoralesValverdeGerman.pruebatec4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.MoralesValverdeGerman.pruebatec4.entity.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {
    @Modifying
    @Query("DELETE FROM Passenger p WHERE p.booking.id = :bookingId")
    void deleteByBookingId(@Param("bookingId") Long bookingId);

}

