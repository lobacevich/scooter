package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.Point;
import by.senla.training.lobacevich.scooter.entity.Scooter;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.entity.enums.ScooterStatus;
import by.senla.training.lobacevich.scooter.mapper.ScooterMapper;
import by.senla.training.lobacevich.scooter.repository.ScooterRepository;
import by.senla.training.lobacevich.scooter.service.PointService;
import by.senla.training.lobacevich.scooter.service.ScooterModelService;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
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
        scooter.setMileage(0.0);
        scooter.setStatus(ScooterStatus.VACANT);
        Scooter savedScooter = scooterRepository.save(scooter);
        log.info("Scooter with id={} and model {} was add", savedScooter.getId(),
                model.getName());
        return scooterMapper.scooterToDto(savedScooter);
    }

    @Override
    public MessageResponse deleteScooter(Long id) {
        scooterRepository.deleteById(id);
        log.info("Scooter with id={} was delete", id);
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
}
