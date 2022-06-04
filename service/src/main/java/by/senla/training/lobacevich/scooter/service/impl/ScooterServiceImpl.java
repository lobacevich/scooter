package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.Point;
import by.senla.training.lobacevich.scooter.entity.Scooter;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.mapper.ScooterMapper;
import by.senla.training.lobacevich.scooter.repository.ScooterRepository;
import by.senla.training.lobacevich.scooter.service.PointService;
import by.senla.training.lobacevich.scooter.service.ScooterModelService;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScooterServiceImpl implements ScooterService {

    private final ScooterRepository scooterRepository;
    private final ScooterModelService scooterModelService;
    private final ScooterMapper scooterMapper;
    private final PointService pointService;

    @Override
    public Scooter getScooterById(Long id) throws NotFoundException {
        return scooterRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Incorrect scooter id"));
    }

    @Override
    public List<ScooterDto> getAllScooters() {
        return scooterRepository.findAll().stream()
                .map(scooterMapper::scooterToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ScooterDto createScooter(ScooterDto scooterDto) throws NotFoundException {
        ScooterModel model = scooterModelService.getById(scooterDto.getModelId());
        Point point = pointService.getById(scooterDto.getPointId());
        Scooter scooter = new Scooter(model, point);
        return scooterMapper.scooterToDto(scooterRepository.save(scooter));
    }

    @Override
    public MessageResponse deleteScooter(Long id) {
        scooterRepository.deleteById(id);
        return new MessageResponse("Scooter was delete successfully");
    }

    @Override
    public ScooterDto updateScooter(Long id, ScooterDto scooterDto) throws NotFoundException {
        Scooter scooter = getScooterById(id);
        ScooterModel model = scooterModelService.getById(scooterDto.getModelId());
        Point point = pointService.getById(scooterDto.getPointId());
        scooter.setModel(model);
        scooter.setPoint(point);
        return scooterMapper.scooterToDto(scooterRepository.save(scooter));
    }

    @Override
    public List<ScooterDto> getScootersByPointId(Long pointId) {
        return scooterRepository.findScootersByPointId(pointId).stream()
                .map(scooterMapper::scooterToDto)
                .collect(Collectors.toList());
    }
}
