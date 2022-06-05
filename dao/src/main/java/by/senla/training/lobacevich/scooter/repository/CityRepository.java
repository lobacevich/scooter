package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
