package by.bsuir.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "passports")
public class Passport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 8, updatable = false, nullable = false)
    private Integer id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 40, nullable = false)
    private String surname;

    @Column(length = 20)
    private String patronymic;

    private Date birthdate;
}
