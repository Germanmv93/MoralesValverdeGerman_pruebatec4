package com.MoralesValverdeGerman.pruebatec4;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.modelmapper.ModelMapper;

import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.service.impl.HotelServiceImpl;
import com.MoralesValverdeGerman.pruebatec4.dto.HotelDto;
import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelAlreadyExistsException;

@ExtendWith(SpringExtension.class)
public class CreateHotelTest {

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private HotelDto hotelDto;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotelDto = new HotelDto(); //
        hotel = new Hotel(); //
    }

    @Test
    void whenCreateHotelGivenHotelDoesNotExist_thenHotelShouldBeCreated() {
        when(modelMapper.map(any(HotelDto.class), any())).thenReturn(hotel);
        when(hotelRepository.save(any(Hotel.class))).thenReturn(hotel);
        when(modelMapper.map(any(Hotel.class), any())).thenReturn(hotelDto);

        HotelDto createdHotel = hotelService.createHotel(hotelDto);

        assertEquals(hotelDto, createdHotel); // Asume que hotelDto tiene un identificador u otra propiedad única para verificar
        verify(hotelRepository).save(any(Hotel.class));
    }

    @Test
    void whenCreateHotelGivenHotelExists_thenExceptionShouldBeThrown() {
        // Configura el mock para simular que el hotel ya existe
        when(hotelRepository.existsByHotelCode(any(String.class))).thenReturn(true);

        HotelDto hotelDto = new HotelDto();
        hotelDto.setHotelCode("HOTEL123");

        // Verifica que se lanza la excepción cuando se intenta crear el hotel
        assertThrows(HotelAlreadyExistsException.class, () -> hotelService.createHotel(hotelDto));
    }
}

