package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.PaymentRequestTo;
import by.bsuir.backend.model.dto.response.PaymentResponseTo;
import by.bsuir.backend.model.entity.Balance;
import by.bsuir.backend.model.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(target = "balance", expression = "java(balanceFromRequest)")
    Payment toEntity(PaymentRequestTo request, @Context Balance balanceFromRequest);

    @Mapping(target = "balance", expression = "java(balanceFromUpdateRequest)")
    Payment updateEntity(@MappingTarget Payment entityToUpdate, PaymentRequestTo updateRequest,
                           @Context Balance balanceFromUpdateRequest);

    @Mapping(target = "balanceId", source = "balance.id")
    PaymentResponseTo toResponseTo(Payment entity);
}
