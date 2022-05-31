package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.OrderRent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderRent, Long> {
}
