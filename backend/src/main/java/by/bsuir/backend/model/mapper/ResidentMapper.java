package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.ResidentRequestTo;
import by.bsuir.backend.model.dto.response.ResidentResponseTo;
import by.bsuir.backend.model.entity.Passport;
import by.bsuir.backend.model.entity.Resident;
import by.bsuir.backend.model.entity.Contract;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = Contract.class)
public interface ResidentMapper {

    @Mapping(target = "passport", expression = "java(passportFromRequest)")
    @Mapping(target = "contracts", ignore = true)
    Resident toEntity(ResidentRequestTo request, @Context Passport passportFromRequest);

    @Mapping(target = "passport", expression = "java(passportFromUpdateRequest)")
    @Mapping(target = "contracts", ignore = true)
    Resident updateEntity(@MappingTarget Resident entityToUpdate, ResidentRequestTo updateRequest,
                          @Context Passport passportFromUpdateRequest);

    @Mapping(target = "passportId", source = "passport.id")
    @Mapping(target = "accountId", source = "passport.account.id")
    @Mapping(target = "contracts", expression = "java(entity.getContracts().stream().map(Contract::getId).toList())")
    ResidentResponseTo toResponseTo(Resident entity);
}
