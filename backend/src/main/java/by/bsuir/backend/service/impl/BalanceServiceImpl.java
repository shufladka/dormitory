package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.BalanceRequestTo;
import by.bsuir.backend.model.dto.response.BalanceResponseTo;
import by.bsuir.backend.model.mapper.BalanceMapper;
import by.bsuir.backend.repository.BalanceRepository;
import by.bsuir.backend.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository repository;

    private final BalanceMapper mapper;
    private final String entityName = "Balance";

    @Override
    public BalanceResponseTo save(BalanceRequestTo requestTo) {
        return Optional.of(requestTo)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<BalanceResponseTo> findAll(Pageable restriction) {
        return repository.findAllByDeletedAtIsNull(restriction).stream()
                .map(mapper::toResponseTo)
                .toList();
    }

    @Override
    public BalanceResponseTo findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public BalanceResponseTo update(BalanceRequestTo requestTo) {
        return repository.findById(requestTo.id())
                .map(entityToUpdate -> {
                    mapper.updateEntity(entityToUpdate, requestTo);
                    entityToUpdate.setUpdatedAt(LocalDateTime.now());
                    return entityToUpdate;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(debt -> {
                    debt.setDeletedAt(LocalDateTime.now());
                    repository.save(debt);
                }, () -> {
                    throw new EntityNotFoundException(entityName, id);
                });
    }
}
