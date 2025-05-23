package by.bsuir.backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "residents")
public class Resident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 8, updatable = false, nullable = false)
    private Integer id;

    @OneToOne()
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @OneToOne()
    @JoinColumn(name = "balance_id")
    private Balance balance;

    @ManyToMany
    @JoinTable(
            name = "residents_contracts",
            joinColumns = @JoinColumn(name = "resident_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    @Where(clause = "deleted_at IS NULL")
    private List<Contract> contracts;
}
