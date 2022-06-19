package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterModelDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.mapper.ScooterModelMapper;
import by.senla.training.lobacevich.scooter.repository.ScooterModelRepository;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScooterModelServiceImplTest {

    public static final Long ID = 1L;
    public static final String NOT_FOUND_MESSAGE = "Incorrect model id";
    public static final String MODEL_NAME = "КUGOO S3";
    @Mock
    private ScooterModelRepository scooterModelRepository;
    @Mock
    private ScooterModelMapper scooterModelMapper;
    @InjectMocks
    private ScooterModelServiceImpl scooterModelService;
    @Mock
    private ScooterModel model;
    @Mock
    private ScooterModelDto modelDto;
    @Mock
    private ScooterModel model2;
    @Mock
    private ScooterModelDto modelDto2;

    @Test
    public void ScooterModelServiceImpl_getById() throws NotFoundException {
        when(scooterModelRepository.findById(ID)).thenReturn(Optional.of(model));

        ScooterModel resultModel = scooterModelService.getById(ID);

        assertNotNull(resultModel);
        assertEquals(model, resultModel);
    }

    @Test
    public void ScooterModelServiceImpl_getById_NotFoundException() {
        when(scooterModelRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> scooterModelService.getById(ID),
                NOT_FOUND_MESSAGE);
    }

    @Test
    public void ScooterModelServiceImpl_createModel() throws CreationException {
        when(scooterModelRepository.save(any(ScooterModel.class))).thenReturn(model);
        when(scooterModelMapper.scooterModelToDto(model)).thenReturn(modelDto);

        ScooterModelDto resultModelDto = scooterModelService.createModel(modelDto);

        assertNotNull(resultModelDto);
        assertEquals(modelDto, resultModelDto);
    }

    @Test
    public void ScooterModelServiceImpl_updateModel() throws NotFoundException {
        ScooterModel modelEntity = new ScooterModel();
        when(scooterModelRepository.findById(ID)).thenReturn(Optional.of(modelEntity));
        when(modelDto.getName()).thenReturn(MODEL_NAME);
        when(scooterModelRepository.save(modelEntity)).thenReturn(modelEntity);
        when(scooterModelMapper.scooterModelToDto(modelEntity)).thenReturn(modelDto);

        ScooterModelDto resultModelDto = scooterModelService.updateModel(ID, modelDto);

        assertEquals(modelDto, resultModelDto);
        assertEquals(MODEL_NAME, modelEntity.getName());
    }

    @Test
    public void ScooterModelServiceImpl_getAllModels() {
        when(scooterModelRepository.findAll()).thenReturn(List.of(model, model2));
        when(scooterModelMapper.scooterModelToDto(model)).thenReturn(modelDto);
        when(scooterModelMapper.scooterModelToDto(model2)).thenReturn(modelDto2);

        List<ScooterModelDto> modelDtoList = scooterModelService.getAllModels();

        assertArrayEquals(Arrays.array(modelDto, modelDto2), modelDtoList.toArray());
    }
}
