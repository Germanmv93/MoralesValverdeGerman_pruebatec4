package com.MoralesValverdeGerman.pruebatec4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MoralesValverdeGerman.pruebatec4.entity.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {
    // Aquí puedes agregar consultas personalizadas si es necesario.
    // Por ejemplo, encontrar pasajeros por nombre, apellido, etc.
}

