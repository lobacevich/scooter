package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.ScooterException;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.entity.Scooter;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.entity.ScooterStatus;
import by.senla.training.lobacevich.scooter.entity.Tariff;
import by.senla.training.lobacevich.scooter.mapper.ScooterMapper;
import by.senla.training.lobacevich.scooter.repository.ScooterModelRepository;
import by.senla.training.lobacevich.scooter.repository.ScooterRepository;
import by.senla.training.lobacevich.scooter.repository.TariffRepository;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ScooterServiceImpl implements ScooterService {

    private final ScooterRepository scooterRepository;
    private final TariffRepository tariffRepository;
    private final ScooterModelRepository modelRepository;
    private final ScooterMapper scooterMapper;

    private Scooter getScooterById(Long id) throws ScooterException {
        return scooterRepository.findById(id).orElseThrow(() ->
                new ScooterException("Incorrect scooter's id"));
    }

    @Override
    public ScooterDto createScooter(ScooterDto scooterDto) throws ScooterException {
        Tariff tariff = tariffRepository.findById(scooterDto.getTariffId()).orElseThrow(() ->
                new ScooterException("Incorrect tariff's id"));
        ScooterModel model = modelRepository.findById(scooterDto.getModelId()).orElseThrow(() ->
                new ScooterException("Incorrect model's id"));
        Scooter scooter = new Scooter(tariff, model, ScooterStatus.VACANT);
        return scooterMapper.scooterToDto(scooterRepository.save(scooter));
    }

    @Override
    public void deleteScooter(Long id) {
        scooterRepository.deleteById(id);
    }

    @Override
    public ScooterDto updateScooter(ScooterDto scooterDto) throws ScooterException {
        Scooter scooter = getScooterById(scooterDto.getId());
        Tariff tariff = tariffRepository.findById(scooterDto.getTariffId()).orElseThrow(() ->
                new ScooterException("Incorrect tariff's id"));
        scooter.setTariff(tariff);
        ScooterModel model = modelRepository.findById(scooterDto.getModelId()).orElseThrow(() ->
                new ScooterException("Incorrect model's id"));
        scooter.setModel(model);
        return scooterMapper.scooterToDto(scooter);
    }
}
