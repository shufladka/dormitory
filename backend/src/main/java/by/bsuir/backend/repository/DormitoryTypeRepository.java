package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.DormitoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormitoryTypeRepository extends JpaRepository<DormitoryType, Integer> {
}
