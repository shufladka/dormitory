package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.EmployeeRequestTo;
import by.bsuir.backend.model.dto.response.EmployeeResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseTo save(EmployeeRequestTo entity);
    List<EmployeeResponseTo> findAll(Pageable restriction);
    EmployeeResponseTo findById(Integer id);
    EmployeeResponseTo update(EmployeeRequestTo updateRequest);
    void delete(Integer id);
}
