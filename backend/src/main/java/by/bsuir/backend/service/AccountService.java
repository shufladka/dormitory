package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AccountResponseTo save(AccountRequestTo entity);
    List<AccountResponseTo> findAll(Pageable restriction);
    AccountResponseTo findById(Integer id);
    AccountResponseTo update(AccountRequestTo updateRequest);
    void delete(Integer id);
    Optional<AccountResponseTo> authorize(String username, String password);
}
