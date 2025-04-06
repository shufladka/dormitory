package by.bsuir.backend.repository;

import by.bsuir.backend.model.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    List<Block> findAllByDormitoryId(Integer dormitoryId);
}
