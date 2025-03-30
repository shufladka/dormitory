package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.DormitoryRequestTo;
import by.bsuir.backend.model.dto.response.DormitoryResponseTo;
import by.bsuir.backend.model.entity.*;
import by.bsuir.backend.model.mapper.DormitoryMapper;
import by.bsuir.backend.repository.*;
import by.bsuir.backend.service.DormitoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DormitoryServiceImpl implements DormitoryService {

    private final DormitoryRepository repository;
    private final AddressRepository addressRepository;
    private final DormitoryTypeRepository dormitoryTypeRepository;

    private final DormitoryMapper mapper;
    private final String entityName = "Dormitory";

    @Override
    public DormitoryResponseTo save(DormitoryRequestTo requestTo) {
        Address addressFromRequest = addressRepository
                .findById(requestTo.addressId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.addressId()));
        DormitoryType dormitoryTypeFromRequest = dormitoryTypeRepository
                .findById(requestTo.dormitoryTypeId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.dormitoryTypeId()));

        return Optional.of(requestTo)
                .map(request -> mapper
                        .toEntity(request, addressFromRequest, dormitoryTypeFromRequest))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<DormitoryResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public DormitoryResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public DormitoryResponseTo update(DormitoryRequestTo requestTo) {
        Address addressFromRequest = addressRepository
                .findById(requestTo.addressId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.addressId()));
        DormitoryType dormitoryTypeFromRequest = dormitoryTypeRepository
                .findById(requestTo.dormitoryTypeId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.dormitoryTypeId()));

        return repository.findById(requestTo.id())
                .map(entityToUpdate -> mapper.updateEntity(entityToUpdate, requestTo,
                        addressFromRequest, dormitoryTypeFromRequest))
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
