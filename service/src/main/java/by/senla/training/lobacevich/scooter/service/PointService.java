package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.Point;

import java.util.List;

public interface PointService {

    Point getById(Long id) throws NotFoundException;

    PointDto createPoint(PointDto pointDto) throws NotFoundException;

    MessageResponse deletePoint(Long id);

    PointDto updatePoint(Long id, PointDto pointDto) throws NotFoundException;

    List<ScooterDto> getPointScooters(Long pointId) throws NotFoundException;

    List<PointDto> getPoints(Integer latitude, Integer longitude);
}
