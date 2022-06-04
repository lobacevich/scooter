package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.entity.Scooter;
import org.springframework.stereotype.Component;

@Component
public class ScooterMapper {

    public ScooterDto scooterToDto(Scooter scooter) {
        ScooterDto scooterDto = new ScooterDto();
        scooterDto.setId(scooter.getId());
        scooterDto.setModelId(scooter.getModel().getId());
        scooterDto.setStatus(scooter.getStatus().name());
        if (scooter.getPoint() != null) {
            scooterDto.setPointId(scooter.getPoint().getId());
        }
        scooterDto.setMileage(scooter.getMileage());
        return scooterDto;
    }
}
