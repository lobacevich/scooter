package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.PointException;
import by.senla.training.lobacevich.scooter.dto.PointDto;

public interface PointService {

    PointDto createPoint(PointDto pointDto);

    void deletePoint(Long id);

    PointDto updatePoint(PointDto pointDto) throws PointException;
}
