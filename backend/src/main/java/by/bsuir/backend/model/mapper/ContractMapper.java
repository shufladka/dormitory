package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.ContractRequestTo;
import by.bsuir.backend.model.dto.response.ContractResponseTo;
import by.bsuir.backend.model.entity.*;
import by.bsuir.backend.model.entity.Contract;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractMapper {

    @Mapping(target = "block", expression = "java(blockFromRequest)")
    @Mapping(target = "status", expression = "java(statusFromRequest)")
    Contract toEntity(ContractRequestTo request, @Context Block blockFromRequest,
                      @Context Status statusFromRequest);

    @Mapping(target = "block", expression = "java(blockFromUpdateRequest)")
    @Mapping(target = "status", expression = "java(statusFromUpdateRequest)")
    Contract updateEntity(@MappingTarget Contract entityToUpdate, ContractRequestTo updateRequest,
                           @Context Block blockFromUpdateRequest,
                           @Context Status statusFromUpdateRequest);

    @Mapping(target = "blockId", source = "block.id")
    @Mapping(target = "statusId", source = "status.id")
    ContractResponseTo toResponseTo(Contract entity);
}
