package by.senla.training.lobacevich.scooter.entity;

import by.senla.training.lobacevich.scooter.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderRent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Scooter scooter;
    @ManyToOne(fetch = FetchType.LAZY)
    private Point startPoint;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Point endPoint;
    @Column
    private LocalDateTime closedDate;
    @Column
    private Double mileage;
    @Column
    private BigDecimal totalCost;
    @Column(nullable = false)
    private Boolean bySeasonTicket;


    public OrderRent(User user, Scooter scooter, Point startPoint, LocalDateTime createdDate) {
        this.user = user;
        this.scooter = scooter;
        this.startPoint = startPoint;
        this.createdDate = createdDate;
    }
}
