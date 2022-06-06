package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.CreationException;
import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.TariffDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import by.senla.training.lobacevich.scooter.entity.Tariff;
import by.senla.training.lobacevich.scooter.mapper.TariffMapper;
import by.senla.training.lobacevich.scooter.repository.TariffRepository;
import by.senla.training.lobacevich.scooter.service.ScooterModelService;
import by.senla.training.lobacevich.scooter.service.TariffService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final ScooterModelService scooterModelService;
    private final TariffMapper tariffMapper;

    @Override
    public Tariff getById(Long id) throws NotFoundException {
        return tariffRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Incorrect tariff id"));
    }

    @Override
    public TariffDto createTariff(TariffDto tariffDto) throws NotFoundException, CreationException {
        if (tariffRepository.existsByModel_Id(tariffDto.getModelId())) {
            throw new CreationException("Tariff for this model already exists");
        }
        ScooterModel model = scooterModelService.getById(tariffDto.getModelId());
        Tariff tariff = new Tariff(model, tariffDto.getPricePerFirstHour(), tariffDto.getPricePerNextHour(),
                tariffDto.getPricePerFirstDay(), tariffDto.getPricePerNextDay());
        return tariffMapper.tariffToDto(tariffRepository.save(tariff));
    }

    @Override
    public TariffDto updateTariff(Long id, TariffDto tariffDto) throws NotFoundException {
        Tariff tariff = getById(id);
        tariff.setModel(scooterModelService.getById(tariffDto.getModelId()));
        tariff.setPricePerFirstHour(tariffDto.getPricePerFirstHour());
        tariff.setPricePerNextHour(tariffDto.getPricePerNextHour());
        tariff.setPricePerFirstDay(tariffDto.getPricePerFirstDay());
        tariff.setPricePerNextDay(tariffDto.getPricePerNextDay());
        return tariffMapper.tariffToDto(tariffRepository.save(tariff));
    }

    @Override
    public List<TariffDto> getAllTariffs() {
        return tariffRepository.findAll().stream()
                .map(tariffMapper::tariffToDto)
                .collect(Collectors.toList());
    }
}
