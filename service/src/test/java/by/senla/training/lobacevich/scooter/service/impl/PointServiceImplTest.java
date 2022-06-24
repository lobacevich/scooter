package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.entity.City;
import by.senla.training.lobacevich.scooter.entity.Point;
import by.senla.training.lobacevich.scooter.mapper.PointMapper;
import by.senla.training.lobacevich.scooter.repository.PointRepository;
import by.senla.training.lobacevich.scooter.service.CityService;
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
public class PointServiceImplTest {

    public static final Long ID = 1L;
    public static final String NOT_FOUND_MESSAGE = "Incorrect point id";
    public static final String DELETE_MESSAGE = "Point was deleted successfully";
    public static final String ADDRESS = "3B, Beruta str., off. 405";
    @Mock
    private PointRepository pointRepository;
    @Mock
    private PointMapper pointMapper;
    @Mock
    private CityService cityService;
    @InjectMocks
    private PointServiceImpl pointService;
    @Mock
    private Point point;
    @Mock
    private PointDto pointDto;
    @Mock
    private Point point2;
    @Mock
    private PointDto pointDto2;
    @Mock
    private City city;

    @Test
    public void PointServiceImpl_getById() throws NotFoundException {
        when(pointRepository.findById(ID)).thenReturn(Optional.of(point));

        Point resultPoint = pointService.getById(ID);

        assertNotNull(resultPoint);
        assertEquals(point, resultPoint);
    }

    @Test
    public void PointServiceImpl_getById_NotFoundException() {
        when(pointRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> pointService.getById(ID),
                NOT_FOUND_MESSAGE);
    }

    @Test
    public void PointServiceImpl_createPoint() throws NotFoundException {
        when(pointDto.getCityId()).thenReturn(ID);
        when(cityService.getById(ID)).thenReturn(city);
        when(pointRepository.save(any(Point.class))).thenReturn(point);
        when(pointMapper.pointToDto(point)).thenReturn(pointDto);

        PointDto resultPointDto = pointService.createPoint(pointDto);

        assertNotNull(resultPointDto);
        assertEquals(pointDto, resultPointDto);
    }

    @Test
    public void PointServiceImpl_deletePoint() {
        assertEquals(DELETE_MESSAGE, pointService.deletePoint(ID).getMessage());
    }

    @Test
    public void PointServiceImpl_updatePoint() throws NotFoundException {
        Point pointEntity = new Point();
        when(pointRepository.findById(ID)).thenReturn(Optional.of(pointEntity));
        when(pointDto.getAddress()).thenReturn(ADDRESS);
        when(pointRepository.save(pointEntity)).thenReturn(pointEntity);
        when(pointMapper.pointToDto(pointEntity)).thenReturn(pointDto);

        PointDto resultPointDto = pointService.updatePoint(ID, pointDto);

        assertEquals(pointDto, resultPointDto);
        assertEquals(ADDRESS, pointEntity.getAddress());
    }

    @Test
    public void PointServiceImpl_getPoints_withParams() {
        PointDto pointDto1 = PointDto.builder()
                .latitude(54)
                .longitude(53)
                .build();
        PointDto pointDto2 = PointDto.builder()
                .latitude(50)
                .longitude(30)
                .build();
        when(pointRepository.findAll()).thenReturn(List.of(point2, point));
        when(pointMapper.pointToDto(point)).thenReturn(pointDto1);
        when(pointMapper.pointToDto(point2)).thenReturn(pointDto2);

        List<PointDto> pointDtoList = pointService.getPoints(50, 50);

        assertEquals(2, pointDtoList.size());
        assertEquals(pointDto1, pointDtoList.get(0));
        assertEquals(5.0, pointDto1.getDistance());
        assertEquals(20.0, pointDto2.getDistance());
    }

    @Test
    public void PointServiceImpl_getPoints_withoutParams() {
        PointDto pointDto1 = PointDto.builder()
                .latitude(54)
                .longitude(53)
                .build();
        PointDto pointDto2 = PointDto.builder()
                .latitude(50)
                .longitude(30)
                .build();
        when(pointRepository.findAll()).thenReturn(List.of(point2, point));
        when(pointMapper.pointToDto(point)).thenReturn(pointDto1);
        when(pointMapper.pointToDto(point2)).thenReturn(pointDto2);

        List<PointDto> pointDtoList = pointService.getPoints(null, null);

        assertEquals(2, pointDtoList.size());
        assertEquals(pointDto2, pointDtoList.get(0));
        assertNull(pointDto1.getDistance());
    }

    @Test
    public void PointServiceImpl_getCityPoints() {
        when(pointRepository.findByCityId(ID)).thenReturn(List.of(point, point2));
        when(pointMapper.pointToDto(point)).thenReturn(pointDto);
        when(pointMapper.pointToDto(point2)).thenReturn(pointDto2);

        List<PointDto> pointDtoList = pointService.getCityPoints(ID);

        assertArrayEquals(Arrays.array(pointDto, pointDto2), pointDtoList.toArray());
    }
}
