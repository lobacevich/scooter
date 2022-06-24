package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.ScooterModelDto;
import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import org.springframework.stereotype.Component;

@Component
public class ScooterModelMapper {

    public ScooterModelDto scooterModelToDto(ScooterModel model) {
        return ScooterModelDto.builder()
                .id(model.getId())
                .name(model.getName())
                .maxSpeed(model.getMaxSpeed())
                .powerReserveKm(model.getPowerReserveKm())
                .build();
    }
}
