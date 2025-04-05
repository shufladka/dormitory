package by.bsuir.backend.service.impl;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import by.bsuir.backend.model.entity.Account;
import by.bsuir.backend.model.entity.Role;
import by.bsuir.backend.model.mapper.AccountMapper;
import by.bsuir.backend.repository.AccountRepository;
import by.bsuir.backend.repository.RoleRepository;
import by.bsuir.backend.service.AccountService;
import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;
    private final RoleRepository roleRepository;
    private final AccountMapper mapper;
    private final String entityName = "Account";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AccountResponseTo save(AccountRequestTo requestTo) {
        List<Role> roles = new ArrayList<>();
        if (requestTo.roleIds() != null && !requestTo.roleIds().isEmpty()) {
            roles = roleRepository.findAllById(requestTo.roleIds());
        }

        Account account = mapper.toEntity(new AccountRequestTo(
                requestTo.id(),
                requestTo.username(),
                encodePassword(requestTo.password()),
                null
        ));

        account.setRoles(roles);

        return Optional.of(repository.save(account))
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public Optional<AccountResponseTo> authorize(String username, String password) {
        return repository.findByUsername(username)
                .filter(account -> passwordEncoder.matches(password, account.getPassword()))
                .map(mapper::toResponseTo);
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
                        entity.setPassword(encodePassword(requestTo.password()));
                    }
                    if (requestTo.roleIds() != null) {
                        List<Role> roles = roleRepository.findAllById(requestTo.roleIds());
                        entity.setRoles(roles);
                    }
                    return entity;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.id()));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }

    /**
     * Method for password hashing throw bcrypt
     * @param password Raw password
     * @return Encrypted password
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
