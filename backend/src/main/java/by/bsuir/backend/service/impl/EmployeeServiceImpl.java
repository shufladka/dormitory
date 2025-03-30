package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.EmployeeRequestTo;
import by.bsuir.backend.model.dto.response.EmployeeResponseTo;
import by.bsuir.backend.model.entity.Passport;
import by.bsuir.backend.model.entity.Position;
import by.bsuir.backend.model.mapper.EmployeeMapper;
import by.bsuir.backend.repository.PassportRepository;
import by.bsuir.backend.repository.PositionRepository;
import by.bsuir.backend.repository.EmployeeRepository;
import by.bsuir.backend.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final PassportRepository passportRepository;
    private final PositionRepository positionRepository;

    private final EmployeeMapper mapper;
    private final String entityName = "Employee";

    @Override
    public EmployeeResponseTo save(EmployeeRequestTo requestTo) {
        Passport passportFromRequest = passportRepository
                .findById(requestTo.passportId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.passportId()));
        Position positionFromRequest = positionRepository
                .findById(requestTo.positionId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.positionId()));

        return Optional.of(requestTo)
                .map(request -> mapper
                        .toEntity(request, passportFromRequest, positionFromRequest))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<EmployeeResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public EmployeeResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public EmployeeResponseTo update(EmployeeRequestTo requestTo) {
        Passport passportFromRequest = passportRepository
                .findById(requestTo.passportId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.passportId()));
        Position positionFromRequest = positionRepository
                .findById(requestTo.positionId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.positionId()));

        return repository.findById(requestTo.id())
                .map(entityToUpdate -> mapper.updateEntity(entityToUpdate, requestTo,
                        passportFromRequest, positionFromRequest))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }
}
