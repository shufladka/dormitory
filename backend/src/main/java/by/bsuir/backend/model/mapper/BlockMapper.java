package by.bsuir.backend.model.mapper;

import by.bsuir.backend.model.dto.request.BlockRequestTo;
import by.bsuir.backend.model.dto.response.BlockResponseTo;
import by.bsuir.backend.model.entity.Block;
import by.bsuir.backend.model.entity.Dormitory;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BlockMapper {

    @Mapping(target = "dormitory", expression = "java(dormitoryFromRequest)")
    Block toEntity(BlockRequestTo request, @Context Dormitory dormitoryFromRequest);

    @Mapping(target = "dormitory", expression = "java(dormitoryFromUpdateRequest)")
    Block updateEntity(@MappingTarget Block entityToUpdate, BlockRequestTo updateRequest,
                           @Context Dormitory dormitoryFromUpdateRequest);

    @Mapping(target = "dormitoryId", source = "dormitory.id")
    BlockResponseTo toResponseTo(Block entity);
}
