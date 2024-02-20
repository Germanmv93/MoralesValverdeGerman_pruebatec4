package com.MoralesValverdeGerman.pruebatec4.repository;

import com.MoralesValverdeGerman.pruebatec4.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByHotel_HotelCodeAndHotel_LocationAndRoomTypeAndIsAvailable(String hotelCode, String location, String roomType, boolean isAvailable);
    List<Room> findByHotel_HotelCodeAndRoomTypeAndIsAvailable(String hotelCode, String roomType, boolean isAvailable);
    @Query("select r from Room r where r.roomNumber = :roomNumber and r.hotel.hotelCode = :hotelCode")
    Optional<Room> findByRoomNumberAndHotel_HotelCode(@Param("roomNumber") String roomNumber, @Param("hotelCode") String hotelCode);
}
