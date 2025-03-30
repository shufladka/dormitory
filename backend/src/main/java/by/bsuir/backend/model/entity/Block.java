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
@Table(name = "blocks")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 8, updatable = false, nullable = false)
    private Integer id;

    @ManyToOne()
    @JoinColumn(name = "dormitory_id")
    private Dormitory dormitory;

    @Column(length = 2, nullable = false)
    private Integer roomNumber;

    @Column(name="is_gasified", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean isGasified;

    @Column(name="is_bathroom_separated", nullable = false, columnDefinition = "BIT", length = 1)
    private Boolean isBathroomSeparated;

    @Column(length = 2, nullable = false)
    private Integer capacity;

    @Column(length = 3, nullable = false)
    private Integer floor;
}
