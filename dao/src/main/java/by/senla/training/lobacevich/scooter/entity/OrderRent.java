package by.senla.training.lobacevich.scooter.entity;

import by.senla.training.lobacevich.scooter.entity.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Point endPoint;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    @Column
    private LocalDateTime closedDate;
    @Column(nullable = false)
    private Double mileage;
    @Column
    private BigDecimal totalPrice;
}
