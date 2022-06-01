package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class PointDto {

    private Long id;
    @NotBlank
    private String location;
    @Min(value = 0, message = "Latitude cannot be less than 0")
    @Max(value = 100, message = "Latitude cannot be more than 100")
    private Integer latitude;
    @Min(value = 0, message = "longitude cannot be less than 0")
    @Max(value = 100, message = "longitude cannot be more than 100")
    private Integer longitude;
    private Double distance;
}
