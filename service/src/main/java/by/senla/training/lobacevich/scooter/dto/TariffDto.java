package by.senla.training.lobacevich.scooter.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class TariffDto {

    private Long id;
    @NotNull(message = "Model id is required")
    private Long modelId;
    private String modelName;
    @NotNull(message = "Price per first hour is required")
    private BigDecimal pricePerFirstHour;
    @NotNull(message = "Price per next hour is required")
    private BigDecimal pricePerNextHour;
    @NotNull(message = "Price per first day is required")
    private BigDecimal pricePerFirstDay;
    @NotNull(message = "Price per next day is required")
    private BigDecimal pricePerNextDay;
}
