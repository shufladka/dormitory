package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import by.bsuir.backend.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {
    Account toEntity(AccountRequestTo request);
    Account updateEntity(@MappingTarget Account entityToUpdate, AccountRequestTo updateRequest);
    AccountResponseTo toResponseTo(Account entity);
}
