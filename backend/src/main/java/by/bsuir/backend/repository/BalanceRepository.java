package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Balance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT b FROM Balance b LEFT JOIN FETCH b.payments p WHERE b.deletedAt IS NULL AND (p.deletedAt IS NULL OR p.deletedAt IS NULL)")
    Optional<Balance> findByIdAndDeletedAtIsNull(Integer id);
}
