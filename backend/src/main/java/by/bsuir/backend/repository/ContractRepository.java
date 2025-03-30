package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findAllByDeletedAtIsNull(Pageable pageable);
    Optional<Contract> findByIdAndDeletedAtIsNull(Integer id);
}
