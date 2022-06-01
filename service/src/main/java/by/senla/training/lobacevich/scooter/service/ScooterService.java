package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.ScooterException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;

public interface ScooterService {

    ScooterDto createScooter(ScooterDto scooterDto) throws ScooterException;

    void deleteScooter(Long id) throws ScooterException;

    ScooterDto updateScooter(ScooterDto scooterDto) throws ScooterException;
}
