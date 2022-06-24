package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.entity.OrderRent;
import org.springframework.stereotype.Component;

@Component
public class OrderRentMapper {

    public OrderRentDto orderRentToDto(OrderRent orderRent) {
        return OrderRentDto.builder()
                .id(orderRent.getId())
                .userId(orderRent.getUser().getId())
                .scooterId(orderRent.getScooter().getId())
                .startPointId(orderRent.getStartPoint().getId())
                .status(orderRent.getStatus())
                .createdDate(orderRent.getCreatedDate())
                .endPointId(orderRent.getEndPoint() == null ? null : orderRent.getEndPoint().getId())
                .closedDate(orderRent.getClosedDate())
                .mileage(orderRent.getMileage())
                .totalCost(orderRent.getTotalCost())
                .build();
    }
}
