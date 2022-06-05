package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findByCityId(Long cityId);
}
