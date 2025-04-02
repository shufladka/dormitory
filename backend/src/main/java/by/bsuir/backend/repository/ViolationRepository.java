package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Violation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViolationRepository extends JpaRepository<Violation, Integer> {
    List<Violation> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Violation> findByIdAndDeletedAtIsNull(Integer id);
}
