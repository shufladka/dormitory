package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.PassportRequestTo;
import by.bsuir.backend.model.dto.response.PassportResponseTo;
import by.bsuir.backend.model.entity.Account;
import by.bsuir.backend.model.entity.Address;
import by.bsuir.backend.model.entity.Contact;
import by.bsuir.backend.model.mapper.PassportMapper;
import by.bsuir.backend.repository.AccountRepository;
import by.bsuir.backend.repository.AddressRepository;
import by.bsuir.backend.repository.ContactRepository;
import by.bsuir.backend.repository.PassportRepository;
import by.bsuir.backend.service.PassportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {

    private final PassportRepository repository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;

    private final PassportMapper mapper;
    private final String entityName = "Passport";

    @Override
    public PassportResponseTo save(PassportRequestTo requestTo) {
        Address addressFromRequest = addressRepository
                .findById(requestTo.addressId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.addressId()));
        Contact contactFromRequest = contactRepository
                .findById(requestTo.contactId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.contactId()));
        Account accountFromRequest = accountRepository
                .findById(requestTo.accountId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.accountId()));

        return Optional.of(requestTo)
                .map(request -> mapper
                        .toEntity(request, addressFromRequest,
                                contactFromRequest, accountFromRequest))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<PassportResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public PassportResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public PassportResponseTo update(PassportRequestTo requestTo) {
        Address addressFromRequest = addressRepository
                .findById(requestTo.addressId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.addressId()));
        Contact contactFromRequest = contactRepository
                .findById(requestTo.contactId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.contactId()));
        Account accountFromRequest = accountRepository
                .findById(requestTo.accountId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.accountId()));

        return repository.findById(requestTo.id())
                .map(entityToUpdate -> mapper.updateEntity(entityToUpdate, requestTo,
                        addressFromRequest, contactFromRequest, accountFromRequest))
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
