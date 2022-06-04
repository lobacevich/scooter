package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tariff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private ScooterModel model;
    @Column(nullable = false)
    private BigDecimal pricePerHour;

    public Tariff(ScooterModel model, BigDecimal pricePerHour) {
        this.model = model;
        this.pricePerHour = pricePerHour;
    }
}
