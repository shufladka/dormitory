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
@Table(name = "dormitory_types")
public class DormitoryType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 4, updatable = false, nullable = false)
    private Integer id;

    @Column(length = 20, nullable = false)
    private String name;
}
