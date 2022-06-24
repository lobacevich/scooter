package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.TariffDto;
import by.senla.training.lobacevich.scooter.entity.Tariff;
import org.springframework.stereotype.Component;

@Component
public class TariffMapper {

    public TariffDto tariffToDto(Tariff tariff) {
        return TariffDto.builder()
                .id(tariff.getId())
                .modelId(tariff.getModel().getId())
                .modelName(tariff.getModel().getName())
                .pricePerFirstHour(tariff.getPricePerFirstHour())
                .pricePerNextHour(tariff.getPricePerNextHour())
                .pricePerFirstDay(tariff.getPricePerFirstDay())
                .pricePerNextDay(tariff.getPricePerNextDay())
                .build();
    }
}
