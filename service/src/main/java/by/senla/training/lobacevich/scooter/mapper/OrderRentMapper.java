package by.senla.training.lobacevich.scooter.mapper;

import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.entity.OrderRent;
import org.springframework.stereotype.Component;

@Component
public class OrderRentMapper {

    public OrderRentDto orderRentToDto(OrderRent orderRent) {
        OrderRentDto orderRentDto = new OrderRentDto();
        orderRentDto.setId(orderRent.getId());
        orderRentDto.setUserId(orderRent.getUser().getId());
        orderRentDto.setScooterId(orderRent.getScooter().getId());
        orderRentDto.setStartPointId(orderRent.getStartPoint().getId());
        orderRentDto.setStatus(orderRent.getStatus());
        orderRentDto.setCreatedDate(orderRent.getCreatedDate());
        orderRentDto.setEndPointId(orderRent.getEndPoint() == null ? null :
                orderRent.getEndPoint().getId());
        orderRentDto.setClosedDate(orderRent.getClosedDate());
        orderRentDto.setMileage(orderRent.getMileage());
        orderRentDto.setTotalCost(orderRent.getTotalCost());
        return orderRentDto;
    }
}
