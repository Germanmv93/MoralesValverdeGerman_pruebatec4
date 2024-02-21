package com.MoralesValverdeGerman.pruebatec4;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import com.MoralesValverdeGerman.pruebatec4.entity.Hotel;
import com.MoralesValverdeGerman.pruebatec4.exception.HotelNotFoundException;
import com.MoralesValverdeGerman.pruebatec4.repository.HotelRepository;
import com.MoralesValverdeGerman.pruebatec4.service.impl.HotelServiceImpl;

@ExtendWith(MockitoExtension.class)
public class DeleteHotelTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HotelServiceImpl hotelService;

    private final String hotelCode = "Nh Cordoba";
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel(); // Configura tu entidad Hotel según sea necesario
        hotel.setHotelCode(hotelCode); // Asegúrate de configurar el código del hotel
    }

    @Test
    void whenDeleteHotelWithValidCode_thenHotelShouldBeDeleted() {
        when(hotelRepository.findByHotelCode(hotelCode)).thenReturn(Optional.of(hotel));
        doNothing().when(hotelRepository).delete(hotel);

        hotelService.deleteHotel(hotelCode);

        verify(hotelRepository, times(1)).delete(hotel);
    }

    @Test
    void whenDeleteHotelWithInvalidCode_thenThrowHotelNotFoundException() {
        when(hotelRepository.findByHotelCode("INVALID_CODE")).thenReturn(Optional.empty());

        assertThrows(HotelNotFoundException.class, () -> hotelService.deleteHotel("INVALID_CODE"));

        verify(hotelRepository, never()).delete(any(Hotel.class));
    }
}

