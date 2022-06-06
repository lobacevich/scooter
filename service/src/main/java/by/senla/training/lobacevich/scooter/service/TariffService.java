package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.TariffDto;
import by.senla.training.lobacevich.scooter.entity.Tariff;

import java.util.List;

public interface TariffService {


    Tariff getById(Long id) throws NotFoundException;

    TariffDto createTariff(TariffDto tariffDto) throws NotFoundException, CreationException;

    TariffDto updateTariff(Long id, TariffDto tariffDto) throws NotFoundException;

    List<TariffDto> getAllTariffs();
}
