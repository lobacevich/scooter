package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.entity.Scooter;
import org.springframework.stereotype.Component;

@Component
public class ScooterMapper {

    public ScooterDto scooterToDto(Scooter scooter) {
        return ScooterDto.builder()
                .id(scooter.getId())
                .modelId(scooter.getModel().getId())
                .status(scooter.getStatus())
                .pointId(scooter.getPoint() == null ? null : scooter.getPoint().getId())
                .mileage(scooter.getMileage())
                .build();
    }
}
