package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.service.OrderRentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderRentController {

    private final OrderRentService orderRentService;

    @Operation(summary = "Rent a scooter")
    @PostMapping("/scooter/{id}")
    public Object rentScooter(@PathVariable("id") Long scooterId,
                              Principal principal) throws NotFoundException {
        return orderRentService.openRent(principal, scooterId);
    }

    @Operation(summary = "Return rented scooter")
    @PostMapping("/close")
    public Object closeRent(@RequestParam(value = "endPointId") Long endPointId,
                            @RequestParam(value = "mileage") Double mileage,
                            Principal principal) throws NotFoundException {
        return orderRentService.closeRent(principal, endPointId, mileage);
    }

    @Operation(summary = "Get all opened orders")
    @GetMapping("/opened")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderRentDto> getOpenedOrders() {
        return orderRentService.getOpenedOrders();
    }

    @Operation(summary = "Get all closed orders")
    @GetMapping("/closed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderRentDto> getClosedOrders() {
        return orderRentService.getClosedOrders();
    }
}
