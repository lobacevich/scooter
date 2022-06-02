package by.senla.training.lobacevich.scooter.entity;

import by.senla.training.lobacevich.scooter.entity.enums.ScooterStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tariff tariff;
    @ManyToOne(fetch = FetchType.LAZY)
    private ScooterModel model;
    @Enumerated(EnumType.STRING)
    private ScooterStatus status;

    public Scooter(Tariff tariff, ScooterModel model, ScooterStatus status) {
        this.tariff = tariff;
        this.model = model;
        this.status = status;
    }
}
