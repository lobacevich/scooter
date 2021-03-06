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
    @ManyToOne
    private ScooterModel model;
    @Enumerated(EnumType.STRING)
    private ScooterStatus status;
    @ManyToOne
    private Point point;
    @Column
    private Double mileage;

    public Scooter(ScooterModel model, Point point) {
        this.model = model;
        this.point = point;
    }
}
