package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.ViolationRequestTo;
import by.bsuir.backend.model.dto.response.ViolationResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ViolationService {
    ViolationResponseTo save(ViolationRequestTo entity);
    List<ViolationResponseTo> findAll(Pageable restriction);
    ViolationResponseTo findById(Integer id);
    ViolationResponseTo update(ViolationRequestTo updateRequest);
    void delete(Integer id);
}
