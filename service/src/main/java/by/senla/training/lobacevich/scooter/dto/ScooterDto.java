package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class ScooterDto {

    private Long id;
    @Min(1)
    private Long modelId;
    @Min(1)
    private Long tariffId;
}
