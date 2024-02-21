package com.MoralesValverdeGerman.pruebatec4.config;

import com.MoralesValverdeGerman.pruebatec4.dto.FlightBookingDto;
import com.MoralesValverdeGerman.pruebatec4.entity.FlightBooking;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Personaliza el mapeo para FlightBooking a FlightBookingDto
        modelMapper.addMappings(new PropertyMap<FlightBooking, FlightBookingDto>() {
            @Override
            protected void configure() {
                map().setDate(source.getFlight().getDate());
                map().setOrigin(source.getFlight().getOrigin());
                map().setDestination(source.getFlight().getDestination());
                map().setSeatType(source.getFlight().getSeatType());
                // Asegúrate de agregar aquí cualquier otro mapeo personalizado necesario
            }
        });

        return modelMapper;
    }
}
