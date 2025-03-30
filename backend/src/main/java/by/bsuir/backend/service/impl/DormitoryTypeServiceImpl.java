package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.DormitoryTypeRequestTo;
import by.bsuir.backend.model.dto.response.DormitoryTypeResponseTo;
import by.bsuir.backend.model.mapper.DormitoryTypeMapper;
import by.bsuir.backend.repository.DormitoryTypeRepository;
import by.bsuir.backend.service.DormitoryTypeService;
import by.bsuir.backend.util.AbstractFieldUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DormitoryTypeServiceImpl extends AbstractFieldUpdater implements DormitoryTypeService {

    private final DormitoryTypeRepository repository;
    private final DormitoryTypeMapper mapper;
    private final String entityName = "DormitoryType";

    @Override
    public DormitoryTypeResponseTo save(DormitoryTypeRequestTo requestTo) {
        return Optional.of(requestTo)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }

    @Override
    public List<DormitoryTypeResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public DormitoryTypeResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public DormitoryTypeResponseTo update(DormitoryTypeRequestTo requestTo) {
        return repository.findById(requestTo.id())
                .map(entityToUpdate -> mapper.updateEntity(entityToUpdate, requestTo))
                .map(repository::save)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(String.format(entityName + " with id %s not found", requestTo.id())));
    }

    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }
}
