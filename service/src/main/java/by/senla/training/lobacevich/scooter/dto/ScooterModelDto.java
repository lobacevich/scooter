package by.senla.training.lobacevich.scooter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ScooterModelDto {

    private Long id;
    @NotBlank(message = "Model name is required")
    private String name;
    @NotNull(message = "Max speed is required")
    private Integer maxSpeed;
    @NotNull(message = "Power reserve is required")
    private Integer powerReserveKm;
}
