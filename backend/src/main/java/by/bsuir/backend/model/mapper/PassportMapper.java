package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.PassportRequestTo;
import by.bsuir.backend.model.dto.response.PassportResponseTo;
import by.bsuir.backend.model.entity.Account;
import by.bsuir.backend.model.entity.Address;
import by.bsuir.backend.model.entity.Contact;
import by.bsuir.backend.model.entity.Passport;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PassportMapper {

    @Mapping(target = "address", expression = "java(addressFromRequest)")
    @Mapping(target = "contact", expression = "java(contactFromRequest)")
    @Mapping(target = "account", expression = "java(accountFromRequest)")
    Passport toEntity(PassportRequestTo request, @Context Address addressFromRequest,
                      @Context Contact contactFromRequest, @Context Account accountFromRequest);

    @Mapping(target = "address", expression = "java(addressFromUpdateRequest)")
    @Mapping(target = "contact", expression = "java(contactFromUpdateRequest)")
    @Mapping(target = "account", expression = "java(accountFromUpdateRequest)")
    Passport updateEntity(@MappingTarget Passport entityToUpdate, PassportRequestTo updateRequest,
                          @Context Address addressFromUpdateRequest, @Context Contact contactFromUpdateRequest,
                          @Context Account accountFromUpdateRequest);

    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "contactId", source = "contact.id")
    @Mapping(target = "accountId", source = "account.id")
    PassportResponseTo toResponseTo(Passport entity);
}
