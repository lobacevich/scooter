package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class ScooterDto {

    private Long id;
    @NotBlank(message = "Model is required")
    private String model;
    @Min(1)
    private Long pointId;
    @Min(1)
    private Long tariffId;
}
