package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.ContractRequestTo;
import by.bsuir.backend.model.dto.response.ContractResponseTo;
import by.bsuir.backend.model.entity.Block;
import by.bsuir.backend.model.entity.Contract;
import by.bsuir.backend.model.entity.Status;
import by.bsuir.backend.model.mapper.ContractMapper;
import by.bsuir.backend.repository.BlockRepository;
import by.bsuir.backend.repository.ContractRepository;
import by.bsuir.backend.repository.StatusRepository;
import by.bsuir.backend.service.ContractService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repository;
    private final BlockRepository blockRepository;
    private final StatusRepository statusRepository;

    private final ContractMapper mapper;
    private final String entityName = "Contract";

    @Override
    public ContractResponseTo save(ContractRequestTo requestTo) {
        Block blockFromRequest = blockRepository.findById(requestTo.blockId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.blockId()));

        Status statusFromRequest = requestTo.statusId() != null ?
                statusRepository.findById(requestTo.statusId())
                        .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.statusId())) :
                null;

        return Optional.of(requestTo)
                .map(request -> {
                    Contract contract = mapper.toEntity(request, blockFromRequest, statusFromRequest);
                    contract.setCreatedAt(LocalDateTime.now());
                    contract.setRentPrice(BigDecimal.valueOf(60.00));
                    return contract;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }


    @Override
    public List<ContractResponseTo> findAll(Pageable restriction) {
        return repository.findAllByDeletedAtIsNull(restriction).stream()
                .map(mapper::toResponseTo)
                .toList();
    }

    @Override
    public ContractResponseTo findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public ContractResponseTo update(ContractRequestTo requestTo) {
        Block blockFromRequest = blockRepository
                .findById(requestTo.blockId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.blockId()));

        Status statusFromRequest = requestTo.statusId() != null ?
                statusRepository.findById(requestTo.statusId())
                        .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.statusId())) :
                null;

        // Находим существующий контракт для проверки текущего статуса
        Contract existingContract = repository.findById(requestTo.id())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.id()));

        return Optional.of(existingContract)
                .map(entityToUpdate -> {
                    // Обновляем все поля контракта кроме deleted_at
                    mapper.updateEntity(entityToUpdate, requestTo, blockFromRequest, statusFromRequest);
                    entityToUpdate.setUpdatedAt(LocalDateTime.now());

                    // Обновляем статус, если был передан новый
                    if (statusFromRequest != null) {
                        entityToUpdate.setStatus(statusFromRequest);
                    }

                    return entityToUpdate;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findByIdIncludingDeleted(id)
                .ifPresentOrElse(contract -> {
                    contract.setDeletedAt(LocalDateTime.now());
                    repository.save(contract);
                }, () -> {
                    throw new EntityNotFoundException(entityName, id);
                });
    }
}
