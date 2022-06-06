package by.senla.training.lobacevich.scooter.dto;

import by.senla.training.lobacevich.scooter.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRentDto {

    private Long id;
    private Long userId;
    private Long scooterId;
    private Long startPointId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;
    private OrderStatus status;
    private Long endPointId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime closedDate;
    private Double mileage;
    private BigDecimal totalCost;
}
