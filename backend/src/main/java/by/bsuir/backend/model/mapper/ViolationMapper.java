package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.ViolationRequestTo;
import by.bsuir.backend.model.dto.response.ViolationResponseTo;
import by.bsuir.backend.model.entity.Resident;
import by.bsuir.backend.model.entity.Violation;
import by.bsuir.backend.model.entity.Status;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ViolationMapper {

    @Mapping(target = "resident", expression = "java(residentFromRequest)")
    @Mapping(target = "status", expression = "java(statusFromRequest)")
    Violation toEntity(ViolationRequestTo request, @Context Resident residentFromRequest,
                      @Context Status statusFromRequest);

    @Mapping(target = "resident", expression = "java(residentFromUpdateRequest)")
    @Mapping(target = "status", expression = "java(statusFromUpdateRequest)")
    Violation updateEntity(@MappingTarget Violation entityToUpdate, ViolationRequestTo updateRequest,
                           @Context Resident residentFromUpdateRequest,
                           @Context Status statusFromUpdateRequest);

    @Mapping(target = "residentId", source = "resident.id")
    @Mapping(target = "status", source = "status.name")
    ViolationResponseTo toResponseTo(Violation entity);
}
