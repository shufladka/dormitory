package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.BlockRequestTo;
import by.bsuir.backend.model.dto.response.BlockResponseTo;
import by.bsuir.backend.model.entity.Dormitory;
import by.bsuir.backend.model.mapper.BlockMapper;
import by.bsuir.backend.repository.BlockRepository;
import by.bsuir.backend.repository.DormitoryRepository;
import by.bsuir.backend.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockServiceImpl implements BlockService {

    private final BlockRepository repository;
    private final DormitoryRepository dormitoryRepository;

    private final BlockMapper mapper;
    private final String entityName = "Block";

    @Override
    public BlockResponseTo save(BlockRequestTo requestTo) {
        Dormitory dormitoryFromRequest = dormitoryRepository
                .findById(requestTo.dormitoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.dormitoryId()));

        return Optional.of(requestTo)
                .map(request -> mapper
                        .toEntity(request, dormitoryFromRequest))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<BlockResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public BlockResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public BlockResponseTo update(BlockRequestTo requestTo) {
        Dormitory dormitoryFromRequest = dormitoryRepository
                .findById(requestTo.dormitoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(entityName, requestTo.dormitoryId()));

        return repository.findById(requestTo.id())
                .map(entityToUpdate -> mapper.updateEntity(entityToUpdate, requestTo,
                        dormitoryFromRequest))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() ->
                        new EntityNotFoundException(String
                                .format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }
}
