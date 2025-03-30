package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.StatusRequestTo;
import by.bsuir.backend.model.dto.response.StatusResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {
    StatusResponseTo save(StatusRequestTo entity);
    List<StatusResponseTo> findAll(Pageable restriction);
    StatusResponseTo findById(Integer id);
    StatusResponseTo update(StatusRequestTo updateRequest);
    void delete(Integer id);
}
