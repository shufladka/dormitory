package by.bsuir.backend.service;

import by.bsuir.backend.model.dto.request.PaymentRequestTo;
import by.bsuir.backend.model.dto.response.PaymentResponseTo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentService {
    PaymentResponseTo save(PaymentRequestTo entity);
    List<PaymentResponseTo> findAll(Pageable restriction);
    PaymentResponseTo findById(Integer id);
    void delete(Integer id);
}
