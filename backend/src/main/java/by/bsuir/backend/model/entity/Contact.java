package by.bsuir.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 8, updatable = false, nullable = false)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String provider;

    @Column(length = 10, nullable = false)
    private String code;

    @Column(name="phone_number", length = 7, nullable = false)
    private String phoneNumber;

    @Column(length = 32, nullable = false)
    private String email;
}
