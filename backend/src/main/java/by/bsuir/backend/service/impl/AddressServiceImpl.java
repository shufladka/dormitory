package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.AddressRequestTo;
import by.bsuir.backend.model.dto.response.AddressResponseTo;
import by.bsuir.backend.model.entity.Address;
import by.bsuir.backend.model.entity.Passport;
import by.bsuir.backend.model.mapper.AddressMapper;
import by.bsuir.backend.repository.AddressRepository;
import by.bsuir.backend.repository.PassportRepository;
import by.bsuir.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository repository;
    private final PassportRepository passportRepository;
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
    public AddressResponseTo findByPassportId(Integer passportId) {
        Passport passport = passportRepository.findById(passportId)
                .orElseThrow(() -> new EntityNotFoundException("Passport", passportId));
        Address address = passport.getAddress();
        if (address == null) {
            throw new EntityNotFoundException("Address", passportId);
        }
        return mapper.toResponseTo(address);
    }

    @Override
    public AddressResponseTo update(AddressRequestTo requestTo) {
        return repository.findById(requestTo.id())
                .map(entityToUpdate -> mapper.updateEntity(entityToUpdate, requestTo))
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
}
