package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.CityDto;
import by.senla.training.lobacevich.scooter.entity.City;
import by.senla.training.lobacevich.scooter.mapper.CityMapper;
import by.senla.training.lobacevich.scooter.repository.CityRepository;
import by.senla.training.lobacevich.scooter.service.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

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
    public CityDto createCity(CityDto cityDto) throws CreationException {
        try {
            City city = cityRepository.save(new City(cityDto.getName()));
            log.info("City with id={} and name={} was create", city.getId(), city.getName());
            return cityMapper.cityToDto(city);
        } catch (Exception e) {
            throw new CreationException("City with the same name already exists");
        }
    }
}
