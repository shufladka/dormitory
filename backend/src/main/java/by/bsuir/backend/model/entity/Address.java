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
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 8, updatable = false, nullable = false)
    private Integer id;

    @Column(name="is_city", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean isCity;

    @Column(length = 30, nullable = false)
    private String settlement;

    @Column(length = 60, nullable = false)
    private String street;

    @Column(name="building_number", nullable = false)
    private Integer buildingNumber;

    private String buildingIndex;

    @Column(name="flat_number")
    private Integer flatNumber;

    @Column(length = 8, nullable = false)
    private String zip;
}
