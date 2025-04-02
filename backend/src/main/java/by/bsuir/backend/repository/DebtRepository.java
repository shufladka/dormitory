package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Debt;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Integer> {
    List<Debt> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Debt> findByIdAndDeletedAtIsNull(Integer id);
}
