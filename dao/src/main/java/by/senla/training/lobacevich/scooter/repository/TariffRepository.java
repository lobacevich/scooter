package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {

    Optional<Tariff> findByModel_Id(Long modelId);

    Boolean existsByModel_Id(Long id);
}
