package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String model;
    @ManyToOne(fetch = FetchType.LAZY)
    private Point point;
    @Column(nullable = false)
    private BigDecimal pricePerHour;
    @OneToMany(fetch = FetchType.LAZY)
    private List<OrderRent> orders;
}
