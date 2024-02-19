package com.MoralesValverdeGerman.pruebatec4.repository;

import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel,String> {

    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.rooms r WHERE h.location = :destination AND r.isDeleted = false AND r.isAvailable = TRUE AND NOT EXISTS (SELECT b FROM BookingHotel b WHERE b.room = r AND b.checkIn < :dateTo AND b.checkOut > :dateFrom)")
    List<Hotel> findAvailableHotelsByDateRangeAndDestination(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo, @Param("destination") String destination);
    boolean existsByLocation(String location);

    Optional<Hotel> findByHotelCode(String hotelCode);
}
