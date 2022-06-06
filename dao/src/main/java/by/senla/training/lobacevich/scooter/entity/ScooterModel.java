package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ScooterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private Integer maxSpeed;
    @Column(nullable = false)
    private Integer powerReserveKm;

    public ScooterModel(String name, Integer maxSpeed, Integer powerReserveKm) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.powerReserveKm = powerReserveKm;
    }
}
