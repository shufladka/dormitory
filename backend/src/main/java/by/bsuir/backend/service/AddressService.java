package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.AddressRequestTo;
import by.bsuir.backend.model.dto.response.AddressResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {
    AddressResponseTo save(AddressRequestTo entity);
    List<AddressResponseTo> findAll(Pageable restriction);
    AddressResponseTo findById(Integer id);
    AddressResponseTo findByPassportId(Integer passportId);
    AddressResponseTo update(AddressRequestTo updateRequest);
    void delete(Integer id);
}
