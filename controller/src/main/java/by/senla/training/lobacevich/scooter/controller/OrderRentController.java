package by.senla.training.lobacevich.scooter.controller;

import by.senla.training.lobacevich.scooter.NotFoundException;
import by.senla.training.lobacevich.scooter.dto.OrderRentDto;
import by.senla.training.lobacevich.scooter.dto.response.ValidationErrorResponse;
import by.senla.training.lobacevich.scooter.service.OrderRentService;
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
    private final ValidationErrorResponse validationErrorResponse;

    @PostMapping("/scooter/{id}")
    public Object rentScooter(@PathVariable("id") Long scooterId,
                              Principal principal) throws NotFoundException {
        return orderRentService.openRent(principal, scooterId);
    }

    @PostMapping("/close")
    public Object closeRent(@RequestParam(value = "endPointId") Long endPointId,
                            @RequestParam(value = "mileage") Double mileage,
                            Principal principal) throws NotFoundException {
        return orderRentService.closeRent(principal, endPointId, mileage);
    }

    @GetMapping("/opened")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderRentDto> getOpenedOrders() {
        return orderRentService.getOpenedOrders();
    }

    @GetMapping("/closed")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderRentDto> getClosedOrders() {
        return orderRentService.getClosedOrders();
    }
}
