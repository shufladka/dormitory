package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.StatusRequestTo;
import by.bsuir.backend.model.dto.response.StatusResponseTo;
import by.bsuir.backend.model.entity.Status;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatusMapper {
    Status toEntity(StatusRequestTo request);
    Status updateEntity(@MappingTarget Status entityToUpdate, StatusRequestTo updateRequest);
    StatusResponseTo toResponseTo(Status entity);
}
