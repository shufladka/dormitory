package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Balance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Balance> findByIdAndDeletedAtIsNull(Integer id);
}
