package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.BalanceRequestTo;
import by.bsuir.backend.model.dto.response.BalanceResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BalanceService {
    BalanceResponseTo save(BalanceRequestTo entity);
    List<BalanceResponseTo> findAll(Pageable restriction);
    BalanceResponseTo findById(Integer id);
    BalanceResponseTo update(BalanceRequestTo updateRequest);
    void delete(Integer id);
}
