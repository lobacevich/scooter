package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.entity.Scooter;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.mapper.ScooterMapper;
import by.senla.training.lobacevich.scooter.repository.PointRepository;
import by.senla.training.lobacevich.scooter.repository.ScooterRepository;
import by.senla.training.lobacevich.scooter.service.PointService;
import by.senla.training.lobacevich.scooter.service.ScooterModelService;
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
public class ScooterServiceImplTest {

    public static final Long ID = 1L;
    public static final String NOT_FOUND_SCOOTER_MESSAGE = "Incorrect scooter id";
    public static final String DELETE_MESSAGE = "Scooter was delete successfully";
    public static final String MODEL_NAME = "КUGOO S3";
    public static final String NOT_FOUND_POINT_MESSAGE = "Incorrect point id";
    @Mock
    private ScooterRepository scooterRepository;
    @Mock
    private ScooterModelService scooterModelService;
    @Mock
    private ScooterMapper scooterMapper;
    @Mock
    private PointService pointService;
    @Mock
    private PointRepository pointRepository;
    @InjectMocks
    private ScooterServiceImpl scooterService;
    @Mock
    private Scooter scooter;
    @Mock
    private ScooterDto scooterDto;
    @Mock
    private Scooter scooter2;
    @Mock
    private ScooterDto scooterDto2;
    @Mock
    private ScooterModel model;

    @Test
    public void ScooterServiceImpl_getScooterById() throws NotFoundException {
        when(scooterRepository.findById(ID)).thenReturn(Optional.of(scooter));

        Scooter resultScooter = scooterService.getScooterById(ID);

        assertEquals(scooter, resultScooter);
    }

    @Test
    public void ScooterServiceImpl_getScooterById_NotFoundException() {
        when(scooterRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> scooterService.getScooterById(ID),
                NOT_FOUND_SCOOTER_MESSAGE);
    }

    @Test
    public void ScooterServiceImpl_getAllScooters() {
        when(scooterRepository.findAll()).thenReturn(List.of(scooter, scooter2));
        when(scooterMapper.scooterToDto(scooter)).thenReturn(scooterDto);
        when(scooterMapper.scooterToDto(scooter2)).thenReturn(scooterDto2);

        List<ScooterDto> scooterDtoList = scooterService.getAllScooters();

        assertArrayEquals(Arrays.array(scooterDto, scooterDto2), scooterDtoList.toArray());
    }

    @Test
    public void ScooterServiceImpl_createScooter() throws NotFoundException {
        when(scooterDto.getModelId()).thenReturn(ID);
        when(scooterModelService.getById(ID)).thenReturn(model);
        when(scooterRepository.save(any(Scooter.class))).thenReturn(scooter);
        when(scooterMapper.scooterToDto(scooter)).thenReturn(scooterDto);

        ScooterDto resultScooterDto = scooterService.createScooter(scooterDto);

        assertNotNull(resultScooterDto);
        assertEquals(scooterDto, resultScooterDto);
    }

    @Test
    public void ScooterServiceImpl_deleteScooter() {
        assertEquals(DELETE_MESSAGE, scooterService.deleteScooter(ID).getMessage());
    }

    @Test
    public void ScooterServiceImpl_updateScooter() throws NotFoundException {
        Scooter scooterEntity = new Scooter();
        when(scooterRepository.findById(ID)).thenReturn(Optional.of(scooterEntity));
        when(scooterDto.getModelId()).thenReturn(ID);
        when(scooterModelService.getById(ID)).thenReturn(model);
        when(model.getName()).thenReturn(MODEL_NAME);
        when(scooterRepository.save(scooterEntity)).thenReturn(scooterEntity);
        when(scooterMapper.scooterToDto(scooterEntity)).thenReturn(scooterDto);

        ScooterDto resultScooterDto = scooterService.updateScooter(ID, scooterDto);

        assertNotNull(resultScooterDto);
        assertEquals(scooterDto, resultScooterDto);
        assertEquals(MODEL_NAME, scooterEntity.getModel().getName());
    }

    @Test
    public void ScooterServiceImpl_getPointScooters() throws NotFoundException {
        when(pointRepository.existsById(ID)).thenReturn(true);
        when(scooterRepository.findScootersByPointId(ID)).thenReturn(List.of(scooter, scooter2));
        when(scooterMapper.scooterToDto(scooter)).thenReturn(scooterDto);
        when(scooterMapper.scooterToDto(scooter2)).thenReturn(scooterDto2);

        List<ScooterDto> scooterDtoList = scooterService.getPointScooters(ID);

        assertArrayEquals(Arrays.array(scooterDto, scooterDto2), scooterDtoList.toArray());
    }

    @Test
    public void ScooterServiceImpl_getPointScooters_NotFoundException() {
        when(pointRepository.existsById(ID)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> scooterService.getPointScooters(ID),
                NOT_FOUND_POINT_MESSAGE);
    }
}
