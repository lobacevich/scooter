package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.Scooter;

import java.util.List;

public interface ScooterService {

    Scooter getScooterById(Long id) throws NotFoundException;

    List<ScooterDto> getAllScooters();

    ScooterDto createScooter(ScooterDto scooterDto) throws NotFoundException;

    MessageResponse deleteScooter(Long id);

    ScooterDto updateScooter(Long id, ScooterDto scooterDto) throws NotFoundException;
}
