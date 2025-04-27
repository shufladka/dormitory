package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.ResidentRequestTo;
import by.bsuir.backend.model.dto.response.ResidentResponseTo;
import by.bsuir.backend.model.entity.Balance;
import by.bsuir.backend.model.entity.Passport;
import by.bsuir.backend.model.entity.Resident;
import by.bsuir.backend.model.entity.Contract;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = Contract.class)
public interface ResidentMapper {

    @Mapping(target = "passport", expression = "java(passportFromRequest)")
    @Mapping(target = "balance", expression = "java(balanceFromRequest)")
    @Mapping(target = "contracts", ignore = true)
    Resident toEntity(ResidentRequestTo request, @Context Passport passportFromRequest,
                      @Context Balance balanceFromRequest);

    @Mapping(target = "passport", expression = "java(passportFromUpdateRequest)")
    @Mapping(target = "balance", expression = "java(balanceFromUpdateRequest)")
    @Mapping(target = "contracts", ignore = true)
    Resident updateEntity(@MappingTarget Resident entityToUpdate, ResidentRequestTo updateRequest,
                          @Context Passport passportFromUpdateRequest, @Context Balance balanceFromUpdateRequest);

    @Mapping(target = "passportId", source = "passport.id")
    @Mapping(target = "accountId", source = "passport.account.id")
    @Mapping(target = "balanceId", source = "balance.id")
    @Mapping(target = "contracts", expression = "java(entity.getContracts().stream().map(Contract::getId).toList())")
    ResidentResponseTo toResponseTo(Resident entity);
}
