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
    private String phoneNumber;
    @Column(nullable = false)
    private Integer latitude;
    @Column(nullable = false)
    private Integer longitude;
    @ManyToOne
    private City city;

    public Point(String address, String phoneNumber, Integer latitude, Integer longitude, City city) {
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
    }
}
