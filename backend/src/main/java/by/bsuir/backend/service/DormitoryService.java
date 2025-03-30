package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.DormitoryRequestTo;
import by.bsuir.backend.model.dto.response.DormitoryResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DormitoryService {
    DormitoryResponseTo save(DormitoryRequestTo entity);
    List<DormitoryResponseTo> findAll(Pageable restriction);
    DormitoryResponseTo findById(Integer id);
    DormitoryResponseTo update(DormitoryRequestTo updateRequest);
    void delete(Integer id);
}
