package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.AddressRequestTo;
import by.bsuir.backend.model.dto.response.AddressResponseTo;
import by.bsuir.backend.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address toEntity(AddressRequestTo request);
    Address updateEntity(@MappingTarget Address entityToUpdate, AddressRequestTo updateRequest);
    AddressResponseTo toResponseTo(Address entity);
}
