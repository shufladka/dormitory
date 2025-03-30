package by.bsuir.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "dormitories")
public class Dormitory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 4, updatable = false, nullable = false)
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "address_id")
    private Address address;




    @Column(length = 2, nullable = false)
    private Integer floors;

    @Column(length = 5, nullable = false)
    private Integer blocks;

    @Column(name="has_lift", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean hasLift;
}
