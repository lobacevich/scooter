package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.City;
import by.senla.training.lobacevich.scooter.entity.Point;
import by.senla.training.lobacevich.scooter.mapper.PointMapper;
import by.senla.training.lobacevich.scooter.repository.PointRepository;
import by.senla.training.lobacevich.scooter.service.CityService;
import by.senla.training.lobacevich.scooter.service.PointService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;
    private final CityService cityService;

    @Override
    public Point getById(Long id) throws NotFoundException {
        return pointRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Incorrect point id"));
    }

    @Override
    public PointDto createPoint(PointDto pointDto) throws NotFoundException {
        City city = cityService.getById(pointDto.getCityId());
        Point point = new Point(pointDto.getAddress(), pointDto.getPhoneNumber(),
                pointDto.getLatitude(), pointDto.getLongitude(), city);
        Point savedPoint = pointRepository.save(point);
        log.info("Point with id={} was create in the city {}", savedPoint.getId(),
                city.getName());
        return pointMapper.pointToDto(savedPoint);
    }

    @Override
    public MessageResponse deletePoint(Long id) {
        pointRepository.deleteById(id);
        log.info("Point with id={} was delete", id);
        return new MessageResponse("Point was deleted successfully");
    }

    @Override
    public PointDto updatePoint(Long id, PointDto pointDto) throws NotFoundException {
        Point point = getById(id);
        point.setAddress(pointDto.getAddress());
        point.setPhoneNumber(pointDto.getPhoneNumber());
        point.setLatitude(pointDto.getLatitude());
        point.setLongitude(pointDto.getLongitude());
        return pointMapper.pointToDto(pointRepository.save(point));
    }

    private double calculateDistance(Integer latitude, Integer longitude, PointDto point) {
        return Math.sqrt((Math.pow(latitude - point.getLatitude(), 2) +
                Math.pow(longitude - point.getLongitude(), 2)));
    }

    @Override
    public List<PointDto> getPoints(Integer latitude, Integer longitude) {
        List<PointDto> pointDtoList = pointRepository.findAll().stream()
                .map(pointMapper::pointToDto)
                .collect(Collectors.toList());
        if (latitude == null || longitude == null) {
            return pointDtoList;
        }
        pointDtoList.forEach(x -> x.setDistance(calculateDistance(latitude, longitude, x)));
        pointDtoList.sort(Comparator.comparing(PointDto::getDistance));
        return pointDtoList;
    }

    @Override
    public List<PointDto> getCityPoints(Long cityId) {
        return pointRepository.findByCityId(cityId).stream()
                .map(pointMapper::pointToDto).
                collect(Collectors.toList());
    }
}
