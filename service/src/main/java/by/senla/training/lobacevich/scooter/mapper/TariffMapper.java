package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.TariffDto;
import by.senla.training.lobacevich.scooter.entity.Tariff;
import org.springframework.stereotype.Component;

@Component
public class TariffMapper {

    public TariffDto tariffToDto(Tariff tariff) {
        TariffDto tariffDto = new TariffDto();
        tariffDto.setId(tariff.getId());
        tariffDto.setModelId(tariff.getModel().getId());
        tariffDto.setPricePerHour(tariff.getPricePerHour());
        return tariffDto;
    }
}
