package by.senla.training.lobacevich.scooter.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PointDto {

    private Long id;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Pone number is required")
    private String phoneNumber;
    private Integer latitude;
    private Integer longitude;
    private Double distance;
    @NotNull(message = "City id is required")
    private Long cityId;
}
