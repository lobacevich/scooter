package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.UpdateException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.dto.UserDto;
import by.senla.training.lobacevich.scooter.dto.request.ChangePasswordRequest;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.OrderRentService;
import by.senla.training.lobacevich.scooter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderRentService orderRentService;
    private final ValidationErrorResponse validationErrorResponse;

    @Operation(summary = "Get current user information")
    @GetMapping
    public UserDto getCurrentUser(Principal principal) throws NotFoundException {
        return userService.getCurrentUser(principal);
    }

    @Operation(summary = "Update user")
    @PostMapping
    public UserDto updateUser(@RequestBody UserDto userDto, Principal principal) throws NotFoundException, UpdateException {
        return userService.updateUser(userDto, principal);
    }

    @Operation(summary = "Get all orders of the current user")
    @GetMapping("/orders")
    public List<OrderRentDto> getOrdersHistory(Principal principal) throws NotFoundException {
        return orderRentService.getUserOrders(principal);
    }

    @Operation(summary = "Give role admin to user")
    @PostMapping("/{id}/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDto setAdmin(@PathVariable("id") Long id) throws NotFoundException {
        return userService.setAdmin(id);
    }

    @Operation(summary = "Change password")
    @PostMapping("/password")
    public Object changePassword(Principal principal, @Valid @RequestBody ChangePasswordRequest
            changePasswordRequest, BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return userService.changePassword(principal, changePasswordRequest);
    }
}
