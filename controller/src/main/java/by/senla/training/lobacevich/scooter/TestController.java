package by.senla.training.lobacevich.scooter;

import by.senla.training.lobacevich.scooter.entity.enums.OrderStatus;
import by.senla.training.lobacevich.scooter.repository.OrderRentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    OrderRentRepository orderRentRepository;

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String forUser() {

        return "welcome user";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdmin() {

        return "welcome admin";
    }

    @GetMapping("/test")
    public Object test() {
        return orderRentRepository.existsByUserIdAndStatus(1L, OrderStatus.OPENED);
    }
}
