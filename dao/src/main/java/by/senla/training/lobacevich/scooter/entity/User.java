package by.senla.training.lobacevich.scooter.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String username;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @ElementCollection(targetClass = UserRole.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<UserRole> roles = new HashSet<>();
}
