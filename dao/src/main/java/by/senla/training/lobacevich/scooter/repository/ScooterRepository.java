package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {
}
