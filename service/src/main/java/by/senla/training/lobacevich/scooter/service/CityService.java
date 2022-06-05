package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.entity.City;

import java.util.List;

public interface CityService {

    City getById(Long id) throws NotFoundException;

    List<CityDto> getAllCities();

    CityDto createCity(CityDto cityDto);

    List<PointDto> getCityPoints(Long cityId);
}
