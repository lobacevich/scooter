package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String PhoneNumber;
    @Column(nullable = false)
    private Integer latitude;
    @Column(nullable = false)
    private Integer longitude;
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;

    public Point(String address, Integer latitude, Integer longitude, City city) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
}
