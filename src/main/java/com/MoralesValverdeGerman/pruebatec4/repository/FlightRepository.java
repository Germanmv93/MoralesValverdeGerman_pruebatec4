package com.MoralesValverdeGerman.pruebatec4.repository;

import com.MoralesValverdeGerman.pruebatec4.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, String> {
}
