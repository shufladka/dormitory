package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.ViolationRequestTo;
import by.bsuir.backend.model.dto.response.ViolationResponseTo;
import by.bsuir.backend.model.entity.Resident;
import by.bsuir.backend.model.entity.Violation;
import by.bsuir.backend.model.entity.Status;
import by.bsuir.backend.model.mapper.ViolationMapper;
import by.bsuir.backend.repository.ResidentRepository;
import by.bsuir.backend.repository.ViolationRepository;
import by.bsuir.backend.repository.StatusRepository;
import by.bsuir.backend.service.ViolationService;
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
public class ViolationServiceImpl implements ViolationService {

    private final ViolationRepository repository;
    private final ResidentRepository residentRepository;
    private final StatusRepository statusRepository;

    private final ViolationMapper mapper;
    private final String entityName = "Violation";

    @Override
    public ViolationResponseTo save(ViolationRequestTo requestTo) {
        Resident residentFromRequest = residentRepository.findById(requestTo.residentId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.residentId()));

        Status statusFromRequest = statusRepository.findById(requestTo.statusId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.statusId()));

        return Optional.of(requestTo)
                .map(request -> {
                    Violation violation = mapper.toEntity(request, residentFromRequest, statusFromRequest);
                    violation.setCreatedAt(LocalDateTime.now());
                    return violation;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<ViolationResponseTo> findAll(Pageable restriction) {
        return repository.findAllByDeletedAtIsNull(restriction).stream()
                .map(mapper::toResponseTo)
                .toList();
    }

    @Override
    public ViolationResponseTo findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public ViolationResponseTo update(ViolationRequestTo requestTo) {
        Resident residentFromRequest = residentRepository
                .findById(requestTo.residentId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.residentId()));

        Status statusFromRequest = statusRepository
                .findById(requestTo.statusId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.statusId()));

        return repository.findById(requestTo.id())
                .map(entityToUpdate -> {
                    mapper.updateEntity(entityToUpdate, requestTo, residentFromRequest, statusFromRequest);
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
                .ifPresentOrElse(violation -> {
                    violation.setDeletedAt(LocalDateTime.now());
                    repository.save(violation);
                }, () -> {
                    throw new EntityNotFoundException(entityName, id);
                });
    }

}
