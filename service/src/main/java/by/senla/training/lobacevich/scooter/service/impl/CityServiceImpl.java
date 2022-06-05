package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.entity.City;
import by.senla.training.lobacevich.scooter.mapper.CityMapper;
import by.senla.training.lobacevich.scooter.mapper.PointMapper;
import by.senla.training.lobacevich.scooter.repository.CityRepository;
import by.senla.training.lobacevich.scooter.repository.PointRepository;
import by.senla.training.lobacevich.scooter.service.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final PointRepository pointRepository;
    private final PointMapper pointMapper;

    @Override
    public City getById(Long id) throws NotFoundException {
        return cityRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Incorrect city id"));
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::cityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CityDto createCity(CityDto cityDto) {
        return cityMapper.cityToDto(cityRepository.save(new City(cityDto.getName())));
    }

    @Override
    public List<PointDto> getCityPoints(Long cityId) {
        return pointRepository.findByCityId(cityId).stream()
                .map(pointMapper::pointToDto).
                collect(Collectors.toList());
    }
}
