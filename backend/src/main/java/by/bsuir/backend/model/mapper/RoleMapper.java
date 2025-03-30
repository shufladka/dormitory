package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.RoleRequestTo;
import by.bsuir.backend.model.dto.response.RoleResponseTo;
import by.bsuir.backend.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper {
    Role toEntity(RoleRequestTo request);
    Role updateEntity(@MappingTarget Role entityToUpdate, RoleRequestTo updateRequest);
    RoleResponseTo toResponseTo(Role entity);
}
