package by.senla.training.lobacevich.scooter.dto;

import by.senla.training.lobacevich.scooter.entity.enums.ScooterStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ScooterDto {

    private Long id;
    @NotNull(message = "Model id is required")
    private Long modelId;
    private ScooterStatus status;
    @NotNull(message = "Point id is required")
    private Long pointId;
    private Double mileage;
}
