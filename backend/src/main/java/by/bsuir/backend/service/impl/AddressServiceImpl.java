package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.AddressRequestTo;
import by.bsuir.backend.model.dto.response.AddressResponseTo;
import by.bsuir.backend.model.mapper.AddressMapper;
import by.bsuir.backend.repository.AddressRepository;
import by.bsuir.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final AddressMapper mapper;
    private final String entityName = "Address";

    @Override
    public AddressResponseTo save(AddressRequestTo requestTo) {
        return Optional.of(requestTo)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<AddressResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public AddressResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public AddressResponseTo update(AddressRequestTo requestTo) {
        return repository.findById(requestTo.id())
                .map(entity -> {
                    updateField(requestTo.isCity(), entity::setIsCity);
                    updateField(requestTo.settlement(), entity::setSettlement);
                    updateField(requestTo.street(), entity::setStreet);
                    updateField(requestTo.buildingNumber(), entity::setBuildingNumber);
                    updateField(requestTo.buildingIndex(), entity::setBuildingIndex);
                    updateField(requestTo.flatNumber(), entity::setFlatNumber);
                    updateField(requestTo.zip(), entity::setZip);
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

    private <T> void updateField(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
