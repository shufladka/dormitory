package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import by.bsuir.backend.model.entity.Account;
import by.bsuir.backend.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = Role.class)
public interface AccountMapper {
    @Mapping(target = "roles", ignore = true)
    Account toEntity(AccountRequestTo request);

    @Mapping(target = "roles", ignore = true)
    Account updateEntity(@MappingTarget Account entityToUpdate, AccountRequestTo updateRequest);

    @Mapping(target = "roles", expression = "java(entity.getRoles().stream().map(Role::getName).toList())")
    AccountResponseTo toResponseTo(Account entity);
}
