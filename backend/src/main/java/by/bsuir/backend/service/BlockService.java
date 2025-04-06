package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.BlockRequestTo;
import by.bsuir.backend.model.dto.response.BlockResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlockService {
    BlockResponseTo save(BlockRequestTo entity);
    List<BlockResponseTo> findAll(Pageable restriction);
    List<BlockResponseTo> findAllByDormitoryId(Integer dormitoryId);
    BlockResponseTo findById(Integer id);
    BlockResponseTo update(BlockRequestTo updateRequest);
    void delete(Integer id);
}
