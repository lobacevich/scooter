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
    @OneToOne
    private ScooterModel model;
    @Column(nullable = false)
    private BigDecimal pricePerFirstHour;
    @Column(nullable = false)
    private BigDecimal pricePerNextHour;
    @Column(nullable = false)
    private BigDecimal pricePerFirstDay;
    @Column(nullable = false)
    private BigDecimal pricePerNextDay;

    public Tariff(ScooterModel model, BigDecimal pricePerFirstHour, BigDecimal pricePerNextHour,
                  BigDecimal pricePerFirstDay, BigDecimal pricePerNextDay) {
        this.model = model;
        this.pricePerFirstHour = pricePerFirstHour;
        this.pricePerNextHour = pricePerNextHour;
        this.pricePerFirstDay = pricePerFirstDay;
        this.pricePerNextDay = pricePerNextDay;
    }
}
