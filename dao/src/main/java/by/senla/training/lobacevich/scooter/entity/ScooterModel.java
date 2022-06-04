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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer maxSpeed;
    @Column(nullable = false)
    private Integer batteryChargeKm;

    public ScooterModel(String name, Integer maxSpeed, Integer batteryChargeKm) {
        this.name = name;
        this.maxSpeed = maxSpeed;
        this.batteryChargeKm = batteryChargeKm;
    }
}
