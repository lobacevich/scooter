package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.entity.City;
import by.senla.training.lobacevich.scooter.mapper.CityMapper;
import by.senla.training.lobacevich.scooter.repository.CityRepository;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

    public static final String MINSK = "Minsk";
    public static final Long ID = 1L;
    public static final String NOT_FOUND_MESSAGE = "Incorrect city id";
    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;
    @InjectMocks
    private CityServiceImpl cityService;
    @Mock
    private City minsk;
    @Mock
    private CityDto minskDto;
    @Mock
    private City pinsk;
    @Mock
    private CityDto pinskDto;

    @Test
    public void CityServiceImpl_getById() throws NotFoundException {
        when(minsk.getName()).thenReturn(MINSK);
        when(cityRepository.findById(ID)).thenReturn(Optional.of(minsk));

        City resultCity = cityService.getById(ID);

        assertNotNull(resultCity);
        assertEquals(MINSK, resultCity.getName());
    }

    @Test
    public void CityServiceImpl_getById_NotFoundException() {
        when(cityRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> cityService.getById(ID),
                NOT_FOUND_MESSAGE);
    }

    @Test
    public void CityServiceImpl_getAllCities() {
        when(cityRepository.findAll()).thenReturn(List.of(minsk, pinsk));
        when(cityMapper.cityToDto(minsk)).thenReturn(minskDto);
        when(cityMapper.cityToDto(pinsk)).thenReturn(pinskDto);

        List<CityDto> cityDtoList = cityService.getAllCities();

        assertArrayEquals(Arrays.array(minskDto, pinskDto), cityDtoList.toArray());
    }

    @Test
    public void CityServiceImpl_createCity() throws CreationException {
        when(cityRepository.save(any(City.class))).thenReturn(minsk);
        when(cityMapper.cityToDto(minsk)).thenReturn(minskDto);
        when(minskDto.getName()).thenReturn(MINSK);

        CityDto cityDto = cityService.createCity(minskDto);

        assertNotNull(cityDto);
        assertEquals(MINSK, cityDto.getName());
    }
}
