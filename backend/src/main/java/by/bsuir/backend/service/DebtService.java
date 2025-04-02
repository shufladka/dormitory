package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.DebtRequestTo;
import by.bsuir.backend.model.dto.response.DebtResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DebtService {
    DebtResponseTo save(DebtRequestTo entity);
    List<DebtResponseTo> findAll(Pageable restriction);
    DebtResponseTo findById(Integer id);
    DebtResponseTo update(DebtRequestTo updateRequest);
    void delete(Integer id);
}
