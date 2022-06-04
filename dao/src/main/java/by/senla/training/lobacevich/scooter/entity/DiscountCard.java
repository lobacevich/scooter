package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DiscountCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer discountPercent;

    public DiscountCard(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }
}
