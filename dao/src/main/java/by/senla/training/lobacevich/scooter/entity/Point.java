package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer latitude;
    @Column(nullable = false)
    private Integer longitude;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Scooter> scooters;

    public Point(String location, Integer latitude, Integer longitude) {
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
