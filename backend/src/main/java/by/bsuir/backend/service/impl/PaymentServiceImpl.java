package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.PaymentRequestTo;
import by.bsuir.backend.model.dto.response.PaymentResponseTo;
import by.bsuir.backend.model.entity.Balance;
import by.bsuir.backend.model.entity.Contract;
import by.bsuir.backend.model.entity.Payment;
import by.bsuir.backend.model.mapper.PaymentMapper;
import by.bsuir.backend.repository.BalanceRepository;
import by.bsuir.backend.repository.ContractRepository;
import by.bsuir.backend.repository.PaymentRepository;
import by.bsuir.backend.service.PaymentService;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final BalanceRepository balanceRepository;

    private final PaymentMapper mapper;
    private final String entityName = "Payment";

    @Override
    public PaymentResponseTo save(PaymentRequestTo requestTo) {
        Balance balanceFromRequest = balanceRepository.findById(requestTo.balanceId())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.balanceId()));

        return Optional.of(requestTo)
                .map(request -> {
                    Payment Payment = mapper.toEntity(request, balanceFromRequest);
                    Payment.setCreatedAt(LocalDateTime.now());
                    return Payment;
                })
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<PaymentResponseTo> findAll(Pageable restriction) {
        return repository.findAllByDeletedAtIsNull(restriction).stream()
                .map(mapper::toResponseTo)
                .toList();
    }

    @Override
    public PaymentResponseTo findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(payment -> {
                    payment.setDeletedAt(LocalDateTime.now());
                    repository.save(payment);
                }, () -> {
                    throw new EntityNotFoundException(entityName, id);
                });
    }
}
