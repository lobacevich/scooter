package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.PointException;
import by.senla.training.lobacevich.scooter.dto.PointDto;
import by.senla.training.lobacevich.scooter.entity.Point;
import by.senla.training.lobacevich.scooter.mapper.PointMapper;
import by.senla.training.lobacevich.scooter.repository.PointRepository;
import by.senla.training.lobacevich.scooter.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final PointMapper pointMapper;

    private Point getPointById(Long id) throws PointException {
        return pointRepository.findById(id).orElseThrow(() ->
                new PointException("Incorrect point's id"));
    }

    @Override
    public PointDto createPoint(PointDto pointDto) {
        Point point = new Point(pointDto.getLocation(), pointDto.getLatitude(), pointDto.getLongitude());
        return pointMapper.pointToDto(pointRepository.save(point));
    }

    @Override
    public void deletePoint(Long id) {
        pointRepository.deleteById(id);
    }

    @Override
    public PointDto updatePoint(PointDto pointDto) throws PointException {
        Point point = getPointById(pointDto.getId());
        point.setLocation(pointDto.getLocation());
        point.setLatitude(pointDto.getLatitude());
        point.setLongitude(pointDto.getLongitude());
        return pointMapper.pointToDto(point);
    }


}
