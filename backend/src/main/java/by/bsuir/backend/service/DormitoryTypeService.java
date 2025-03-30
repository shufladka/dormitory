package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.DormitoryTypeRequestTo;
import by.bsuir.backend.model.dto.response.DormitoryTypeResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DormitoryTypeService {
    DormitoryTypeResponseTo save(DormitoryTypeRequestTo entity);
    List<DormitoryTypeResponseTo> findAll(Pageable restriction);
    DormitoryTypeResponseTo findById(Integer id);
    DormitoryTypeResponseTo update(DormitoryTypeRequestTo updateRequest);
    void delete(Integer id);
}
