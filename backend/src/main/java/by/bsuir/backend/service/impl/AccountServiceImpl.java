package by.bsuir.backend.service.impl;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import by.bsuir.backend.model.entity.Account;
import by.bsuir.backend.model.mapper.AccountMapper;
import by.bsuir.backend.repository.AccountRepository;
import by.bsuir.backend.service.AccountService;
import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final String entityName = "Account";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AccountResponseTo save(AccountRequestTo requestTo) {
        return Optional.of(requestTo)
                .map(request -> new AccountRequestTo(
                        request.id(),
                        request.username(),
                        encodePassword(request.password()), // Хешируем пароль
                        request.email()
                ))
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    /**
     * Method for authorization
     * @param username
     * @param password
     * @return true | false
     */
    @Override
    public boolean authorize(String username, String password) {
        // Находим пользователя по имени
        Account account = repository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(entityName));

        // Сравниваем введённый пароль с хешированным паролем в базе данных
        return passwordEncoder.matches(password, account.getPassword());
    }

    @Override
    public List<AccountResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public AccountResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public AccountResponseTo update(AccountRequestTo requestTo) {
        return repository.findById(requestTo.id())
                .map(entity -> {
                    if (requestTo.username() != null) {
                        entity.setUsername(requestTo.username());
                    }
                    if (requestTo.password() != null) {
                        entity.setPassword(encodePassword(requestTo.password())); // Хешируем пароль
                    }
                    if (requestTo.email() != null) {
                        entity.setEmail(requestTo.email());
                    }
                    return entity;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(String.format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }

    /**
     * Method for password hashing throw bcrypt
     * @param password
     * @return
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
