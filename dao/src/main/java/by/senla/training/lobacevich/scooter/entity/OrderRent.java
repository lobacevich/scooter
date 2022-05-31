package by.senla.training.lobacevich.scooter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @OneToOne
    private Point startPoint;
    @Enumerated(EnumType.STRING)
    private StatusOrder status;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    @Column(nullable = false)
    private LocalDateTime createdDate;
    @OneToOne
    private Point endPoint;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm")
    @Column
    private LocalDateTime closedDate;
    @Column
    private Double mileage;
}