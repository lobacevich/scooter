package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.TariffDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.entity.Tariff;
import by.senla.training.lobacevich.scooter.mapper.TariffMapper;
import by.senla.training.lobacevich.scooter.repository.TariffRepository;
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
public class TariffServiceImplTest {

    public static final Long ID = 1L;
    public static final String NOT_FOUND_MESSAGE = "Incorrect tariff id";
    public static final String CREATION_EXCEPTION_MESSAGE = "Tariff for this model already exists";
    public static final String MODEL_NAME = "КUGOO S3";
    @Mock
    private TariffRepository tariffRepository;
    @Mock
    private ScooterModelService scooterModelService;
    @Mock
    private TariffMapper tariffMapper;
    @InjectMocks
    private TariffServiceImpl tariffService;
    @Mock
    private Tariff tariff;
    @Mock
    private TariffDto tariffDto;
    @Mock
    private Tariff tariff2;
    @Mock
    private TariffDto tariffDto2;
    @Mock
    private ScooterModel model;

    @Test
    public void TariffServiceImpl_getById() throws NotFoundException {
        when(tariffRepository.findById(ID)).thenReturn(Optional.of(tariff));

        Tariff resultTariff = tariffService.getById(ID);

        assertNotNull(resultTariff);
        assertEquals(tariff, resultTariff);
    }

    @Test
    public void TariffServiceImpl_getById_NotFoundException() {
        when(tariffRepository.findById(ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> tariffService.getById(ID),
                NOT_FOUND_MESSAGE);
    }

    @Test
    public void TariffServiceImpl_createTariff() throws NotFoundException, CreationException {
        when(tariffDto.getModelId()).thenReturn(ID);
        when(tariffRepository.existsByModel_Id(ID)).thenReturn(false);
        when(scooterModelService.getById(ID)).thenReturn(model);
        when(tariffRepository.save(any(Tariff.class))).thenReturn(tariff);
        when(tariffMapper.tariffToDto(tariff)).thenReturn(tariffDto);

        TariffDto resultTariffDto = tariffService.createTariff(tariffDto);

        assertNotNull(resultTariffDto);
        assertEquals(tariffDto, resultTariffDto);
    }

    @Test
    public void TariffServiceImpl_createTariff_CreationException() {
        when(tariffDto.getModelId()).thenReturn(ID);
        when(tariffRepository.existsByModel_Id(ID)).thenReturn(true);

        assertThrows(CreationException.class, () -> tariffService.createTariff(tariffDto),
                CREATION_EXCEPTION_MESSAGE);
    }

    @Test
    public void TariffServiceImpl_updateTariff() throws NotFoundException {
        Tariff tariffEntity = new Tariff();
        when(tariffRepository.findById(ID)).thenReturn(Optional.of(tariffEntity));
        when(tariffDto.getModelId()).thenReturn(ID);
        when(scooterModelService.getById(ID)).thenReturn(model);
        when(model.getName()).thenReturn(MODEL_NAME);
        when(tariffRepository.save(tariffEntity)).thenReturn(tariffEntity);
        when(tariffMapper.tariffToDto(tariffEntity)).thenReturn(tariffDto);

        TariffDto resultTariffDto = tariffService.updateTariff(ID, tariffDto);

        assertNotNull(resultTariffDto);
        assertEquals(tariffDto, resultTariffDto);
        assertEquals(MODEL_NAME, tariffEntity.getModel().getName());
    }

    @Test
    public void TariffServiceImpl_getAllTariffs() {
        when(tariffRepository.findAll()).thenReturn(List.of(tariff, tariff2));
        when(tariffMapper.tariffToDto(tariff)).thenReturn(tariffDto);
        when(tariffMapper.tariffToDto(tariff2)).thenReturn(tariffDto2);

        List<TariffDto> tariffDtoList = tariffService.getAllTariffs();

        assertArrayEquals(Arrays.array(tariffDto, tariffDto2), tariffDtoList.toArray());
    }
}
