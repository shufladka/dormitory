package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.EmployeeRequestTo;
import by.bsuir.backend.model.dto.response.EmployeeResponseTo;
import by.bsuir.backend.model.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {

    @Mapping(target = "passport", expression = "java(passportFromRequest)")
    @Mapping(target = "position", expression = "java(positionFromRequest)")
    Employee toEntity(EmployeeRequestTo request, @Context Passport passportFromRequest,
                      @Context Position positionFromRequest);

    @Mapping(target = "passport", expression = "java(passportFromUpdateRequest)")
    @Mapping(target = "position", expression = "java(positionFromUpdateRequest)")
    Employee updateEntity(@MappingTarget Employee entityToUpdate, EmployeeRequestTo updateRequest,
                          @Context Passport passportFromUpdateRequest, @Context Position positionFromUpdateRequest);

    @Mapping(target = "passportId", source = "passport.id")
    @Mapping(target = "positionId", source = "position.id")
    EmployeeResponseTo toResponseTo(Employee entity);
}
