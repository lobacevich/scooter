package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.ScooterModelDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import org.springframework.stereotype.Component;

@Component
public class ScooterModelMapper {

    public ScooterModelDto scooterModelToDto(ScooterModel model) {
        ScooterModelDto scooterModelDto = new ScooterModelDto();
        scooterModelDto.setId(model.getId());
        scooterModelDto.setName(model.getName());
        scooterModelDto.setMaxSpeed(model.getMaxSpeed());
        scooterModelDto.setBatteryChargeKm(model.getBatteryChargeKm());
        return scooterModelDto;
    }
}
