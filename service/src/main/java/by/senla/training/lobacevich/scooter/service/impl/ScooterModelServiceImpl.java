package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.ScooterModelDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.mapper.ScooterModelMapper;
import by.senla.training.lobacevich.scooter.repository.ScooterModelRepository;
import by.senla.training.lobacevich.scooter.service.ScooterModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScooterModelServiceImpl implements ScooterModelService {

    private final ScooterModelRepository scooterModelRepository;
    private final ScooterModelMapper scooterModelMapper;

    @Override
    public ScooterModel getById(Long id) throws NotFoundException {
        return scooterModelRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Incorrect model id"));
    }

    @Override
    public ScooterModelDto createModel(ScooterModelDto modelDto) {
        ScooterModel model = new ScooterModel(modelDto.getName(),modelDto.getMaxSpeed(),
                modelDto.getPowerReserveKm());
        return scooterModelMapper.scooterModelToDto(scooterModelRepository.save(model));
    }

    @Override
    public ScooterModelDto updateModel(Long id, ScooterModelDto modelDto) throws NotFoundException {
        ScooterModel model = getById(id);
        model.setName(modelDto.getName());
        model.setMaxSpeed(modelDto.getMaxSpeed());
        model.setPowerReserveKm(modelDto.getPowerReserveKm());
        return scooterModelMapper.scooterModelToDto(scooterModelRepository.save(model));
    }

    @Override
    public List<ScooterModelDto> getAllModels() {
        return scooterModelRepository.findAll().stream()
                .map(scooterModelMapper::scooterModelToDto)
                .collect(Collectors.toList());
    }
}
