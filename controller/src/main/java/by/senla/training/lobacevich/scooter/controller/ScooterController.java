package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.dto.ScooterDto;
import by.senla.training.lobacevich.scooter.dto.response.MessageResponse;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.OrderRentService;
import by.senla.training.lobacevich.scooter.service.ScooterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/scooters")
@AllArgsConstructor
public class ScooterController {

    private final ScooterService scooterService;
    private final ValidationErrorResponse validationErrorResponse;
    private final OrderRentService orderRentService;

    @Operation(summary = "Get all scooters")
    @GetMapping
    public List<ScooterDto> getAllScooters() {
        return scooterService.getAllScooters();
    }

    @Operation(summary = "Add a scooter")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Object createScooter(@Valid @RequestBody ScooterDto scooterDto,
                                BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return scooterService.createScooter(scooterDto);
    }

    @Operation(summary = "Update a scooter")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object updateScooter(@PathVariable("id") Long id,@Valid @RequestBody ScooterDto scooterDto,
                               BindingResult bindingResult) throws NotFoundException {
        if (bindingResult.hasErrors()) {
            return validationErrorResponse.getErrors(bindingResult);
        }
        return scooterService.updateScooter(id, scooterDto);
    }

    @Operation(summary = "Delete a scooter")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MessageResponse deleteScooter(@PathVariable("id") Long id) {
        return scooterService.deleteScooter(id);
    }

    @Operation(summary = "Get all orders for a scooter")
    @GetMapping("/{id}/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderRentDto> getOrdersHistory(@PathVariable("id") Long scooterId) {
        return orderRentService.getOrdersByScooterId(scooterId);
    }
}
