package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.PaymentRequestTo;
import by.bsuir.backend.model.dto.response.PaymentResponseTo;
import by.bsuir.backend.model.entity.Contract;
import by.bsuir.backend.model.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(target = "contract", expression = "java(contractFromRequest)")
    Payment toEntity(PaymentRequestTo request, @Context Contract contractFromRequest);

    @Mapping(target = "contract", expression = "java(contractFromUpdateRequest)")
    Payment updateEntity(@MappingTarget Payment entityToUpdate, PaymentRequestTo updateRequest,
                           @Context Contract contractFromUpdateRequest);

    @Mapping(target = "contractId", source = "contract.id")
    PaymentResponseTo toResponseTo(Payment entity);
}
