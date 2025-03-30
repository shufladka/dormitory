package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.PositionRequestTo;
import by.bsuir.backend.model.dto.response.PositionResponseTo;
import by.bsuir.backend.model.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PositionMapper {
    Position toEntity(PositionRequestTo request);
    Position updateEntity(@MappingTarget Position entityToUpdate, PositionRequestTo updateRequest);
    PositionResponseTo toResponseTo(Position entity);
}
