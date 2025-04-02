package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Payment> findByIdAndDeletedAtIsNull(Integer id);
}
