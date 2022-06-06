package by.senla.training.lobacevich.scooter.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScooterDto {

    private Long id;
    @NotNull(message = "Model id is required")
    private Long modelId;
    private String status;
    @NotNull(message = "Point id is required")
    private Long pointId;
    private Double mileage;
}
