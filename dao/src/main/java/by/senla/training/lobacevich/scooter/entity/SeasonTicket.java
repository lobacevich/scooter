package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SeasonTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private LocalDate expirationDate;
    @Column(nullable = false)
    private Integer monthsValid;
    @ManyToOne(fetch = FetchType.LAZY)
    private ScooterModel model;

    public SeasonTicket(ScooterModel model) {
        this.model = model;
    }
}
