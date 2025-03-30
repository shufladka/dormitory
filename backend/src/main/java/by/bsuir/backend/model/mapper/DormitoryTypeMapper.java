package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.DormitoryTypeRequestTo;
import by.bsuir.backend.model.dto.response.DormitoryTypeResponseTo;
import by.bsuir.backend.model.entity.DormitoryType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DormitoryTypeMapper {
    DormitoryType toEntity(DormitoryTypeRequestTo request);
    DormitoryType updateEntity(@MappingTarget DormitoryType entityToUpdate, DormitoryTypeRequestTo updateRequest);
    DormitoryTypeResponseTo toResponseTo(DormitoryType entity);
}
