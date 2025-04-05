package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.PassportRequestTo;
import by.bsuir.backend.model.dto.response.PassportResponseTo;
import by.bsuir.backend.model.entity.Account;
import by.bsuir.backend.model.entity.Address;
import by.bsuir.backend.model.entity.Contact;
import by.bsuir.backend.model.entity.Passport;
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
import org.springframework.transaction.annotation.Transactional;

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
        Address addressFromRequest = (requestTo.addressId() != null) ?
                addressRepository.findById(requestTo.addressId())
                        .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.addressId())) :
                null;

        Contact contactFromRequest = (requestTo.contactId() != null) ?
                contactRepository.findById(requestTo.contactId())
                        .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.contactId())) :
                null;

        Account accountFromRequest = (requestTo.accountId() != null) ?
                accountRepository.findById(requestTo.accountId())
                        .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.accountId())) :
                null;

        Passport passport = mapper.toEntity(requestTo, addressFromRequest, contactFromRequest, accountFromRequest);

        return Optional.of(repository.save(passport))
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
    public PassportResponseTo findByAccountId(Integer accountId) {
        return repository.findByAccountId(accountId)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, accountId));
    }

    @Override
    public PassportResponseTo update(PassportRequestTo requestTo) {
        return repository.findById(requestTo.id())
                .map(entityToUpdate -> {
                    if (requestTo.addressId() != null) {
                        Address address = addressRepository.findById(requestTo.addressId())
                                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.addressId()));
                        entityToUpdate.setAddress(address);
                    }
                    if (requestTo.contactId() != null) {
                        Contact contact = contactRepository.findById(requestTo.contactId())
                                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.contactId()));
                        entityToUpdate.setContact(contact);
                    }
                    if (requestTo.accountId() != null) {
                        Account account = accountRepository.findById(requestTo.accountId())
                                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.accountId()));
                        entityToUpdate.setAccount(account);
                    }

                    if (requestTo.surname() != null) {
                        entityToUpdate.setSurname(requestTo.surname());
                    }
                    if (requestTo.name() != null) {
                        entityToUpdate.setName(requestTo.name());
                    }
                    if (requestTo.patronymic() != null) {
                        entityToUpdate.setPatronymic(requestTo.patronymic());
                    }
                    if (requestTo.birthDate() != null) {
                        entityToUpdate.setBirthDate(requestTo.birthDate());
                    }

                    return repository.save(entityToUpdate);
                })
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }
}
