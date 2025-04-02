package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.DebtRequestTo;
import by.bsuir.backend.model.dto.response.DebtResponseTo;
import by.bsuir.backend.model.entity.Contract;
import by.bsuir.backend.model.entity.Debt;
import by.bsuir.backend.model.entity.Status;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DebtMapper {

    @Mapping(target = "contract", expression = "java(contractFromRequest)")
    @Mapping(target = "status", expression = "java(statusFromRequest)")
    Debt toEntity(DebtRequestTo request, @Context Contract contractFromRequest,
                      @Context Status statusFromRequest);

    @Mapping(target = "contract", expression = "java(contractFromUpdateRequest)")
    @Mapping(target = "status", expression = "java(statusFromUpdateRequest)")
    Debt updateEntity(@MappingTarget Debt entityToUpdate, DebtRequestTo updateRequest,
                           @Context Contract contractFromUpdateRequest,
                           @Context Status statusFromUpdateRequest);

    @Mapping(target = "contractId", source = "contract.id")
    @Mapping(target = "statusId", source = "status.id")
    DebtResponseTo toResponseTo(Debt entity);
}
