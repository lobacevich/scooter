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
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
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
    public Object openRent(Principal principal, Long scooterId) throws NotFoundException {
        Scooter scooter = scooterService.getScooterById(scooterId);
        if (scooter.getStatus() == ScooterStatus.RENTED) {
            return new MessageResponse("Scooter was already rent");
        }
        User user = userService.getUserByPrincipal(principal);
        if (orderRentRepository.existsByUserIdAndStatus(user.getId(), OrderStatus.OPENED)) {
            return new MessageResponse("User already rented a scooter");
        }
        OrderRent orderRent = new OrderRent(user, scooter, scooter.getPoint(), LocalDateTime.now());
        scooter.setStatus(ScooterStatus.RENTED);
        scooter.setPoint(null);
        scooterRepository.save(scooter);
        orderRent.setStatus(OrderStatus.OPENED);
        log.info("Scooter with id={} was rented by user {}", scooter.getId(), user.getUsername());
        return orderRentMapper.orderRentToDto(orderRentRepository.save(orderRent));
    }

    @Override
    public OrderRentDto closeRent(Principal principal, Long endPointId, Double mileage) throws NotFoundException {
        User user = userService.getUserByPrincipal(principal);
        OrderRent orderRent = orderRentRepository.findByUserIdAndStatus(user.getId(), OrderStatus.OPENED)
                .orElseThrow(() -> new NotFoundException("User doesn't have an open order"));
        Point endPoint = pointService.getById(endPointId);
        Scooter scooter = orderRent.getScooter();
        scooter.setPoint(endPoint);
        scooter.setMileage(scooter.getMileage() + mileage);
        scooter.setStatus(ScooterStatus.VACANT);
        orderRent.setEndPoint(endPoint);
        orderRent.setMileage(mileage);
        orderRent.setClosedDate(LocalDateTime.now());
        Tariff tariff = tariffRepository.findByModel_Id(scooter.getModel().getId()).orElseThrow(() ->
                new NotFoundException("Tariff not found"));
        orderRent.setTotalCost(calculateCost(orderRent.getUser().getDiscountCard(),
                orderRent.getCreatedDate(), tariff));
        orderRent.setStatus(OrderStatus.CLOSED);
        log.info("Scooter with id={} that was rented by user {} was returned to point with id={}",
                scooter.getId(), user.getUsername(), endPointId);
        return orderRentMapper.orderRentToDto(orderRentRepository.save(orderRent));
    }

    private BigDecimal calculateCost(DiscountCard discountCard, LocalDateTime beginTime, Tariff tariff) {
        int discount;
        if (discountCard != null) {
            discount = discountCard.getDiscountPercent();
        } else {
            discount = 0;
        }
        return calculateByHours(ChronoUnit.MINUTES.between(beginTime, LocalDateTime.now()), tariff)
                .min(calculateByDays(ChronoUnit.HOURS.between(beginTime, LocalDateTime.now()),
                        tariff)).multiply(BigDecimal.valueOf((100 - discount) / 100.0));
    }

    private BigDecimal calculateByDays(long hoursCount, Tariff tariff) {
        if (hoursCount % 24 <= 1) {
            return tariff.getPricePerFirstDay().add(tariff.getPricePerNextDay().multiply(
                    BigDecimal.valueOf(hoursCount / 24 - 1)));
        }
        return tariff.getPricePerFirstDay().add(tariff.getPricePerNextDay().multiply(
                BigDecimal.valueOf(hoursCount / 24)));
    }

    private BigDecimal calculateByHours(long minutesCount, Tariff tariff) {
        if (minutesCount % 60 <= 5 && minutesCount / 60 > 0) {
            return tariff.getPricePerFirstHour().add(tariff.getPricePerNextHour().multiply(
                    BigDecimal.valueOf(minutesCount / 60 - 1)));
        }
        return tariff.getPricePerFirstHour().add(tariff.getPricePerNextHour().multiply(
                BigDecimal.valueOf(minutesCount / 60)));
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
