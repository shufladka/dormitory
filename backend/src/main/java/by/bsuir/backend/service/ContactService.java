package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.ContactRequestTo;
import by.bsuir.backend.model.dto.response.ContactResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {
    ContactResponseTo save(ContactRequestTo entity);
    List<ContactResponseTo> findAll(Pageable restriction);
    ContactResponseTo findById(Integer id);
    ContactResponseTo findByPassportId(Integer passportId);
    ContactResponseTo update(ContactRequestTo updateRequest);
    void delete(Integer id);
}
