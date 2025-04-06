package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.RoleRequestTo;
import by.bsuir.backend.model.dto.response.RoleResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    RoleResponseTo save(RoleRequestTo entity);
    List<RoleResponseTo> findAll(Pageable restriction);
    RoleResponseTo findById(Integer id);
    RoleResponseTo findByName(String name);
    RoleResponseTo update(RoleRequestTo updateRequest);
    void delete(Integer id);
}
