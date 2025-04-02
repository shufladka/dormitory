package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.DebtRequestTo;
import by.bsuir.backend.model.dto.response.DebtResponseTo;
import by.bsuir.backend.model.entity.Contract;
import by.bsuir.backend.model.entity.Debt;
import by.bsuir.backend.model.entity.Status;
import by.bsuir.backend.model.mapper.DebtMapper;
import by.bsuir.backend.repository.ContractRepository;
import by.bsuir.backend.repository.DebtRepository;
import by.bsuir.backend.repository.StatusRepository;
import by.bsuir.backend.service.DebtService;
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
public class DebtServiceImpl implements DebtService {

    private final DebtRepository repository;
    private final ContractRepository contractRepository;
    private final StatusRepository statusRepository;

    private final DebtMapper mapper;
    private final String entityName = "Debt";

    @Override
    public DebtResponseTo save(DebtRequestTo requestTo) {
        Contract contractFromRequest = contractRepository.findById(requestTo.contractId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.contractId()));

        Status statusFromRequest = statusRepository.findById(requestTo.statusId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.statusId()));

        return Optional.of(requestTo)
                .map(request -> {
                    Debt Debt = mapper.toEntity(request, contractFromRequest, statusFromRequest);
                    Debt.setCreatedAt(LocalDateTime.now());
                    return Debt;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<DebtResponseTo> findAll(Pageable restriction) {
        return repository.findAllByDeletedAtIsNull(restriction).stream()
                .map(mapper::toResponseTo)
                .toList();
    }

    @Override
    public DebtResponseTo findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public DebtResponseTo update(DebtRequestTo requestTo) {
        Contract contractFromRequest = contractRepository
                .findById(requestTo.contractId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.contractId()));

        Status statusFromRequest = statusRepository
                .findById(requestTo.statusId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.statusId()));

        return repository.findById(requestTo.id())
                .map(entityToUpdate -> {
                    mapper.updateEntity(entityToUpdate, requestTo, contractFromRequest, statusFromRequest);
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
