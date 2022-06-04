package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.service.OrderRentService;
import by.senla.training.lobacevich.scooter.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderRentService orderRentService;

    @GetMapping
    public UserDto getCurrentUser(Principal principal) throws NotFoundException {
        return userService.getCurrentUser(principal);
    }

    @PostMapping
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) throws NotFoundException {
        return userService.updateUser(userDto, principal);
    }

    @GetMapping("/history")
    public List<OrderRentDto> getOrdersHistory(Principal principal) throws NotFoundException {
        return orderRentService.getUserOrders(principal);
    }
}
