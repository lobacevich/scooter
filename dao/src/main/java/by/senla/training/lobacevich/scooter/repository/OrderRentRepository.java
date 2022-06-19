package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.OrderRent;
import by.senla.training.lobacevich.scooter.entity.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRentRepository extends JpaRepository<OrderRent, Long> {

    Boolean existsByUserIdAndStatus(Long userId, OrderStatus orderStatus);

    Optional<OrderRent> findByUserIdAndStatus(Long userId, OrderStatus orderStatus);

    List<OrderRent> findByStatus(OrderStatus orderStatus);

    List<OrderRent> findByScooterId(Long scooterId);

    List<OrderRent> findByUserId(Long userId);
}
