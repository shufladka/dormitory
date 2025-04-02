package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.ResidentRequestTo;
import by.bsuir.backend.model.dto.response.ResidentResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ResidentService {
    ResidentResponseTo save(ResidentRequestTo entity);
    List<ResidentResponseTo> findAll(Pageable restriction);
    ResidentResponseTo findById(Integer id);
    ResidentResponseTo update(ResidentRequestTo updateRequest);
    void delete(Integer id);
}
