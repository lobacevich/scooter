package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScooterRepository extends JpaRepository<Scooter, Long> {

    List<Scooter> findScootersByPointId(Long pointId);
}
