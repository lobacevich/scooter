package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.ScooterModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterModelRepository extends JpaRepository<ScooterModel, Long> {
}
