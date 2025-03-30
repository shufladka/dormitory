package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.ContractRequestTo;
import by.bsuir.backend.model.dto.response.ContractResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContractService {
    ContractResponseTo save(ContractRequestTo entity);
    List<ContractResponseTo> findAll(Pageable restriction);
    ContractResponseTo findById(Integer id);
    ContractResponseTo update(ContractRequestTo updateRequest);
    void delete(Integer id);
}
