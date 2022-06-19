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
import by.senla.training.lobacevich.scooter.service.PointService;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import by.senla.training.lobacevich.scooter.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderRentServiceImplTest {

    public static final Long ID = 1L;
    public static final String SCOOTER_RESPONSE = "Scooter was already rent";
    public static final String USER_RESPONSE = "User already rented a scooter";
    @Mock
    private OrderRentRepository orderRentRepository;
    @Mock
    private UserService userService;
    @Mock
    private PointService pointService;
    @Mock
    private ScooterService scooterService;
    @Mock
    private ScooterRepository scooterRepository;
    @Mock
    private TariffRepository tariffRepository;
    @Mock
    private OrderRentMapper orderRentMapper;
    @InjectMocks
    private OrderRentServiceImpl orderRentService;
    @Mock
    private Principal principal;
    @Mock
    private Scooter scooter;
    @Mock
    private User user;
    @Mock
    private OrderRent openedOrderRent;
    @Mock
    private OrderRentDto openedOrderRentDto;
    @Mock
    private OrderRent closedOrderRent;
    @Mock
    private OrderRentDto closedOrderRentDto;
    @Mock
    private Tariff tariff;
    @Mock
    private ScooterModel model;
    @Mock
    private DiscountCard discountCard;

    @Test
    public void OrderRentServiceImpl_openRent() throws NotFoundException {
        when(scooterService.getScooterById(ID)).thenReturn(scooter);
        when(scooter.getStatus()).thenReturn(ScooterStatus.VACANT);
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getId()).thenReturn(ID);
        when(orderRentRepository.existsByUserIdAndStatus(ID, OrderStatus.OPENED)).thenReturn(false);
        when(orderRentRepository.save(any(OrderRent.class))).thenReturn(openedOrderRent);
        when(orderRentMapper.orderRentToDto(openedOrderRent)).thenReturn(openedOrderRentDto);
        when(openedOrderRentDto.getScooterId()).thenReturn(ID);

        OrderRentDto orderRentDto = (OrderRentDto) orderRentService.openRent(principal, ID);

        assertNotNull(orderRentDto);
        assertEquals(ID, orderRentDto.getScooterId());
    }

    @Test
    public void OrderRentServiceImpl_openRent_scooterMessageResponse() throws NotFoundException {
        when(scooterService.getScooterById(ID)).thenReturn(scooter);
        when(scooter.getStatus()).thenReturn(ScooterStatus.RENTED);

        MessageResponse messageResponse = (MessageResponse) orderRentService.openRent(principal, ID);

        assertNotNull(messageResponse);
        assertEquals(SCOOTER_RESPONSE, messageResponse.getMessage());
    }

    @Test
    public void OrderRentServiceImpl_openRent_userMessageResponse() throws NotFoundException {
        when(scooterService.getScooterById(ID)).thenReturn(scooter);
        when(scooter.getStatus()).thenReturn(ScooterStatus.VACANT);
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getId()).thenReturn(ID);
        when(orderRentRepository.existsByUserIdAndStatus(ID, OrderStatus.OPENED)).thenReturn(true);

        MessageResponse messageResponse = (MessageResponse) orderRentService.openRent(principal, ID);

        assertNotNull(messageResponse);
        assertEquals(USER_RESPONSE, messageResponse.getMessage());
    }

    @Test
    public void OrderRentServiceImpl_closeRent_calculateByHours() throws NotFoundException {
        OrderRent orderRentEntity = new OrderRent();
        orderRentEntity.setScooter(scooter);
        orderRentEntity.setUser(user);
        orderRentEntity.setCreatedDate(LocalDateTime.now().minusHours(3));
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getId()).thenReturn(ID);
        when(orderRentRepository.findByUserIdAndStatus(ID, OrderStatus.OPENED)).thenReturn(Optional.of(orderRentEntity));
        when(scooter.getModel()).thenReturn(model);
        when(model.getId()).thenReturn(ID);
        when(tariffRepository.findByModel_Id(ID)).thenReturn(Optional.of(tariff));
        when(user.getDiscountCard()).thenReturn(discountCard);
        when(discountCard.getDiscountPercent()).thenReturn(20);
        when(tariff.getPricePerFirstHour()).thenReturn(BigDecimal.TEN);
        when(tariff.getPricePerNextHour()).thenReturn(BigDecimal.valueOf(5));
        when(tariff.getPricePerFirstDay()).thenReturn(BigDecimal.valueOf(30));
        when(tariff.getPricePerNextDay()).thenReturn(BigDecimal.valueOf(20));
        when(orderRentRepository.save(orderRentEntity)).thenReturn(orderRentEntity);
        when(orderRentMapper.orderRentToDto(orderRentEntity)).thenReturn(openedOrderRentDto);

        OrderRentDto orderRentDto = orderRentService.closeRent(principal, ID, 12.0);

        assertNotNull(orderRentDto);
        assertEquals(BigDecimal.valueOf(16.0), orderRentEntity.getTotalCost());
    }

    @Test
    public void OrderRentServiceImpl_closeRent_calculateByDays() throws NotFoundException {
        OrderRent orderRentEntity = new OrderRent();
        orderRentEntity.setScooter(scooter);
        orderRentEntity.setUser(user);
        orderRentEntity.setCreatedDate(LocalDateTime.now().minusHours(7));
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getId()).thenReturn(ID);
        when(orderRentRepository.findByUserIdAndStatus(ID, OrderStatus.OPENED)).thenReturn(Optional.of(orderRentEntity));
        when(scooter.getModel()).thenReturn(model);
        when(model.getId()).thenReturn(ID);
        when(tariffRepository.findByModel_Id(ID)).thenReturn(Optional.of(tariff));
        when(user.getDiscountCard()).thenReturn(discountCard);
        when(discountCard.getDiscountPercent()).thenReturn(10);
        when(tariff.getPricePerFirstHour()).thenReturn(BigDecimal.TEN);
        when(tariff.getPricePerNextHour()).thenReturn(BigDecimal.valueOf(5));
        when(tariff.getPricePerFirstDay()).thenReturn(BigDecimal.valueOf(30));
        when(tariff.getPricePerNextDay()).thenReturn(BigDecimal.valueOf(20));
        when(orderRentRepository.save(orderRentEntity)).thenReturn(orderRentEntity);
        when(orderRentMapper.orderRentToDto(orderRentEntity)).thenReturn(openedOrderRentDto);

        OrderRentDto orderRentDto = orderRentService.closeRent(principal, ID, 12.0);

        assertNotNull(orderRentDto);
        assertEquals(BigDecimal.valueOf(27.0), orderRentEntity.getTotalCost());
    }

    @Test
    public void OrderRentServiceImpl_getOpenedOrders() {
        when(orderRentRepository.findByStatus(OrderStatus.OPENED)).thenReturn(List.of(openedOrderRent));
        when(orderRentMapper.orderRentToDto(openedOrderRent)).thenReturn(openedOrderRentDto);

        List<OrderRentDto> orderRentDtoList = orderRentService.getOpenedOrders();

        assertEquals(1, orderRentDtoList.size());
        assertEquals(orderRentDtoList.get(0), openedOrderRentDto);
    }

    @Test
    public void OrderRentServiceImpl_getClosedOrders() {
        when(orderRentRepository.findByStatus(OrderStatus.CLOSED)).thenReturn(List.of(closedOrderRent));
        when(orderRentMapper.orderRentToDto(closedOrderRent)).thenReturn(closedOrderRentDto);

        List<OrderRentDto> orderRentDtoList = orderRentService.getClosedOrders();

        assertEquals(1, orderRentDtoList.size());
        assertEquals(orderRentDtoList.get(0), closedOrderRentDto);
    }

    @Test
    public void OrderRentServiceImpl_getOrdersByScooterId() {
        when(orderRentRepository.findByScooterId(ID)).thenReturn(List.of(openedOrderRent, closedOrderRent));
        when(orderRentMapper.orderRentToDto(openedOrderRent)).thenReturn(openedOrderRentDto);
        when(orderRentMapper.orderRentToDto(closedOrderRent)).thenReturn(closedOrderRentDto);

        List<OrderRentDto> orderRentDtoList = orderRentService.getOrdersByScooterId(ID);

        assertEquals(2, orderRentDtoList.size());
        assertEquals(orderRentDtoList.get(0), openedOrderRentDto);
        assertEquals(orderRentDtoList.get(1), closedOrderRentDto);
    }

    @Test
    public void OrderRentServiceImpl_getUserOrders() throws NotFoundException {
        when(userService.getUserByPrincipal(principal)).thenReturn(user);
        when(user.getId()).thenReturn(ID);
        when(orderRentRepository.findByUserId(ID)).thenReturn(List.of(openedOrderRent, closedOrderRent));
        when(orderRentMapper.orderRentToDto(openedOrderRent)).thenReturn(openedOrderRentDto);
        when(orderRentMapper.orderRentToDto(closedOrderRent)).thenReturn(closedOrderRentDto);

        List<OrderRentDto> orderRentDtoList = orderRentService.getUserOrders(principal);

        assertEquals(2, orderRentDtoList.size());
        assertEquals(orderRentDtoList.get(0), openedOrderRentDto);
        assertEquals(orderRentDtoList.get(1), closedOrderRentDto);
    }
}
