package com.MoralesValverdeGerman.pruebatec4.repository;

import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository

public interface FlightBookingRepository extends JpaRepository<FlightBooking, Long> {
//    // Aquí puedes agregar métodos para consultas personalizadas si son necesarias
//
//    // Ejemplo: Encontrar reservas de vuelo por código de vuelo
//    List<FlightBooking> findByFlightCode(String flightCode);
//



    // Ejemplo: Encontrar reservas por origen y destino
    @Query("SELECT fb FROM FlightBooking fb JOIN fb.flight f WHERE f.origin = :origin AND f.destination = :destination")
    List<FlightBooking> findByOriginAndDestination(@Param("origin") String origin, @Param("destination") String destination);

    int countByFlightFlightCode(String flightCode);

    List<FlightBooking> findByFlightFlightCode(String flightCode);

}
