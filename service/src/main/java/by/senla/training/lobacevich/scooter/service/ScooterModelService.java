package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterModelDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;

import java.util.List;

public interface ScooterModelService {

    ScooterModel getById(Long id) throws NotFoundException;

    ScooterModelDto createModel(ScooterModelDto modelDto);

    ScooterModelDto updateModel(Long id, ScooterModelDto modelDto) throws NotFoundException;

    List<ScooterModelDto> getAllModels();
}
