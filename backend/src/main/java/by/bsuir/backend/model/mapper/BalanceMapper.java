package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.BalanceRequestTo;
import by.bsuir.backend.model.dto.response.BalanceResponseTo;
import by.bsuir.backend.model.entity.Balance;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = PaymentMapper.class)
public interface BalanceMapper {
    Balance toEntity(BalanceRequestTo request);
    Balance updateEntity(@MappingTarget Balance entityToUpdate, BalanceRequestTo updateRequest);

    @Mapping(target = "payments", source = "payments")
    BalanceResponseTo toResponseTo(Balance entity);
}
