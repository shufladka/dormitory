package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.PositionRequestTo;
import by.bsuir.backend.model.dto.response.PositionResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PositionService {
    PositionResponseTo save(PositionRequestTo entity);
    List<PositionResponseTo> findAll(Pageable restriction);
    PositionResponseTo findById(Integer id);
    PositionResponseTo update(PositionRequestTo updateRequest);
    void delete(Integer id);
}
