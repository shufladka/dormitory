package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.DormitoryRequestTo;
import by.bsuir.backend.model.dto.response.DormitoryResponseTo;
import by.bsuir.backend.model.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DormitoryMapper {

    @Mapping(target = "address", expression = "java(addressFromRequest)")
    @Mapping(target = "dormitoryType", expression = "java(dormitoryTypeFromRequest)")
    Dormitory toEntity(DormitoryRequestTo request, @Context Address addressFromRequest,
                      @Context DormitoryType dormitoryTypeFromRequest);

    @Mapping(target = "address", expression = "java(addressFromUpdateRequest)")
    @Mapping(target = "dormitoryType", expression = "java(dormitoryTypeFromUpdateRequest)")
    Dormitory updateEntity(@MappingTarget Dormitory entityToUpdate, DormitoryRequestTo updateRequest,
                           @Context Address addressFromUpdateRequest,
                           @Context DormitoryType dormitoryTypeFromUpdateRequest);

    @Mapping(target = "addressId", source = "address.id")
    @Mapping(target = "dormitoryTypeId", source = "dormitoryType.id")
    DormitoryResponseTo toResponseTo(Dormitory entity);
}
