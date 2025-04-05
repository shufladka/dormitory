package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.PassportRequestTo;
import by.bsuir.backend.model.dto.response.PassportResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassportService {
    PassportResponseTo save(PassportRequestTo entity);
    List<PassportResponseTo> findAll(Pageable restriction);
    PassportResponseTo findById(Integer id);
    PassportResponseTo findByAccountId(Integer id);
    PassportResponseTo update(PassportRequestTo updateRequest);
    void delete(Integer id);
}
