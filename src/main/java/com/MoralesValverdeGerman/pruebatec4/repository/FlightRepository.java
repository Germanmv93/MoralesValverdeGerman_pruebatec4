package com.MoralesValverdeGerman.pruebatec4.repository;

import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, String> {
    Optional<Flight> findByFlightCode(String flightCode);
    List<Flight> findByDateBetweenAndOriginAndDestination(LocalDate dateFrom, LocalDate dateTo, String origin, String destination);

}
