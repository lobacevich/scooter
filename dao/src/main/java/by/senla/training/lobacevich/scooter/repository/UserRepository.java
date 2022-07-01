package by.senla.training.lobacevich.scooter.repository;

import by.senla.training.lobacevich.scooter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
