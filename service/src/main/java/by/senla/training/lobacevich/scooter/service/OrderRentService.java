package by.senla.training.lobacevich.scooter.service;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;

import java.security.Principal;
import java.util.List;

public interface OrderRentService {

    Object openRent(Principal principal, Long scooterId) throws NotFoundException;

    OrderRentDto closeRent(Principal principal, Long endPointId, Double mileage) throws NotFoundException;

    List<OrderRentDto> getOpenedOrders();

    List<OrderRentDto> getClosedOrders();

    List<OrderRentDto> getOrdersByScooterId(Long scooterId);

    List<OrderRentDto> getUserOrders(Principal principal) throws NotFoundException;
}
