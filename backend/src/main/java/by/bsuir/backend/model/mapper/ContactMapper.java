package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.ContactRequestTo;
import by.bsuir.backend.model.dto.response.ContactResponseTo;
import by.bsuir.backend.model.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactMapper {
    Contact toEntity(ContactRequestTo request);
    Contact updateEntity(@MappingTarget Contact entityToUpdate, ContactRequestTo updateRequest);
    ContactResponseTo toResponseTo(Contact entity);
}
