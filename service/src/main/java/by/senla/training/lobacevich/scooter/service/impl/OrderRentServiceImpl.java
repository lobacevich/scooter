package by.senla.training.lobacevich.scooter.service.impl;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.entity.*;
import by.senla.training.lobacevich.scooter.entity.enums.OrderStatus;
import by.senla.training.lobacevich.scooter.entity.enums.ScooterStatus;
import by.senla.training.lobacevich.scooter.mapper.OrderRentMapper;
import by.senla.training.lobacevich.scooter.repository.OrderRentRepository;
import by.senla.training.lobacevich.scooter.repository.ScooterRepository;
import by.senla.training.lobacevich.scooter.repository.TariffRepository;
import by.senla.training.lobacevich.scooter.service.OrderRentService;
import by.senla.training.lobacevich.scooter.service.PointService;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderRentServiceImpl implements OrderRentService {

    private final OrderRentRepository orderRentRepository;
    private final UserService userService;
    private final PointService pointService;
    private final ScooterService scooterService;
    private final ScooterRepository scooterRepository;
    private final TariffRepository tariffRepository;
    private final OrderRentMapper orderRentMapper;

    public OrderRent getOrderById(Long id) throws NotFoundException {
        return orderRentRepository.findById(id).orElseThrow(() -> new NotFoundException("Incorrect order id"));
    }

    @Override
    public Object openRent(Principal principal, Long scooterId, Boolean bySeasonTicket) throws NotFoundException {
        Scooter scooter = scooterService.getScooterById(scooterId);
        if (scooter.getStatus() == ScooterStatus.RENTED) {
            return new MessageResponse("Scooter was already rent");
        }
        User user = userService.getUserByPrincipal(principal);
        if (orderRentRepository.existsByUserIdAndStatus(user.getId(), OrderStatus.OPENED)) {
            return new MessageResponse("User just has opened order rent");
        }
        OrderRent orderRent = new OrderRent(user, scooter, scooter.getPoint(), LocalDateTime.now());
        if (bySeasonTicket != null && bySeasonTicket) {
            if (validateSeasonTicket(user.getSeasonTicket(), scooter.getModel())) {
                orderRent.setBySeasonTicket(true);
            } else {
                return new MessageResponse("User haven't valid season ticket");
            }
        } else {
            orderRent.setBySeasonTicket(false);
        }
        scooter.setStatus(ScooterStatus.RENTED);
        scooter.setPoint(null);
        scooterRepository.save(scooter);
        orderRent.setStatus(OrderStatus.OPENED);
        return orderRentMapper.orderRentToDto(orderRentRepository.save(orderRent));
    }

    private boolean validateSeasonTicket(SeasonTicket seasonTicket, ScooterModel scooterModel) {
        return scooterModel.getName().equals(seasonTicket.getModel().getName()) &&
                (seasonTicket.getExpirationDate().isAfter(LocalDate.now()) ||
                        seasonTicket.getExpirationDate().isEqual(LocalDate.now()));
    }

    @Override
    public OrderRentDto closeRent(Principal principal, Long endPointId, Double mileage) throws NotFoundException {
        User user = userService.getUserByPrincipal(principal);
        OrderRent orderRent = orderRentRepository.findByUserIdAndStatus(user.getId(), OrderStatus.OPENED)
                .orElseThrow(() -> new NotFoundException("Order not found"));
        Point endPoint = pointService.getById(endPointId);
        Scooter scooter = orderRent.getScooter();
        scooter.setPoint(endPoint);
        scooter.setMileage(scooter.getMileage() + mileage);
        scooter.setStatus(ScooterStatus.VACANT);
        orderRent.setEndPoint(endPoint);
        orderRent.setMileage(mileage);
        orderRent.setClosedDate(LocalDateTime.now());
        if (!orderRent.getBySeasonTicket()) {
            Tariff tariff = tariffRepository.findByModel_Id(scooter.getModel().getId()).orElseThrow(() ->
                    new NotFoundException("Tariff not found"));
            orderRent.setTotalCost(calculateCost(orderRent.getUser().getDiscountCard(),
                    orderRent.getCreatedDate(), tariff));
        }
        orderRent.setStatus(OrderStatus.CLOSED);
        return orderRentMapper.orderRentToDto(orderRentRepository.save(orderRent));
    }

    private BigDecimal calculateCost(DiscountCard discountCard, LocalDateTime beginTime, Tariff tariff) {
        BigDecimal hours = BigDecimal.valueOf(ChronoUnit.HOURS.between(beginTime,
                LocalDateTime.now()) + 1);
        BigDecimal price = tariff.getPricePerHour().multiply(hours);
        if (discountCard != null) {
            price = price.multiply(BigDecimal.valueOf((100 - discountCard.getDiscountPercent()) / 100));
        }
        return price;
    }

    @Override
    public List<OrderRentDto> getOpenedOrders() {
        return listOrderToDto(orderRentRepository.findByStatus(OrderStatus.OPENED));
    }

    @Override
    public List<OrderRentDto> getClosedOrders() {
        return listOrderToDto(orderRentRepository.findByStatus(OrderStatus.CLOSED));
    }

    @Override
    public List<OrderRentDto> getOrdersByScooterId(Long scooterId) {
        return listOrderToDto(orderRentRepository.findByScooterId(scooterId));
    }

    @Override
    public List<OrderRentDto> getUserOrders(Principal principal) throws NotFoundException {
        User user = userService.getUserByPrincipal(principal);
        return listOrderToDto(orderRentRepository.findByUserId(user.getId()));
    }

    private List<OrderRentDto> listOrderToDto(List<OrderRent> orderRentList) {
        return orderRentList.stream()
                .map(orderRentMapper::orderRentToDto)
                .collect(Collectors.toList());
    }
}
