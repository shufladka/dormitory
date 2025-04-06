package by.bsuir.backend.service.impl;

import by.bsuir.backend.exception.EntityNotFoundException;
import by.bsuir.backend.exception.EntitySavingException;
import by.bsuir.backend.model.dto.request.ResidentRequestTo;
import by.bsuir.backend.model.dto.response.ResidentResponseTo;
import by.bsuir.backend.model.entity.Passport;
import by.bsuir.backend.model.entity.Resident;
import by.bsuir.backend.model.entity.Contract;
import by.bsuir.backend.model.mapper.ResidentMapper;
import by.bsuir.backend.repository.PassportRepository;
import by.bsuir.backend.repository.ResidentRepository;
import by.bsuir.backend.repository.ContractRepository;
import by.bsuir.backend.service.ResidentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResidentServiceImpl implements ResidentService {

    private final ResidentRepository repository;
    private final PassportRepository passportRepository;
    private final ContractRepository contractRepository;
    private final ResidentMapper mapper;
    private final String entityName = "Resident";

    @Override
    public ResidentResponseTo save(ResidentRequestTo requestTo) {
        log.info("Saving resident with passportId: {}", requestTo.passportId());

        Passport passportFromRequest = passportRepository
                .findById(requestTo.passportId())
                .orElseThrow(() -> {
                    log.error("Passport with id {} not found", requestTo.passportId());
                    return new EntityNotFoundException(entityName, requestTo.passportId());
                });

        log.info("Found passport: {}", passportFromRequest);

        List<Contract> contracts = contractRepository.findAllById(requestTo.contracts());
        log.info("Contracts found: {}", contracts.size());

        if (contracts.size() != requestTo.contracts().size()) {
            log.error("Some contracts were not found. Expected: {}, Found: {}",
                    requestTo.contracts().size(), contracts.size());
            throw new EntityNotFoundException("Некоторые контракты не найдены");
        }

        Resident resident = mapper.toEntity(requestTo, passportFromRequest);
        resident.setContracts(contracts);

        try {
            Resident savedResident = repository.save(resident);
            log.info("Resident saved with id: {}", savedResident.getId());
            return mapper.toResponseTo(savedResident);
        } catch (Exception e) {
            log.error("Error while saving resident: {}", e.getMessage());
            throw new EntitySavingException(entityName, requestTo.id());
        }
    }

    @Override
    public List<ResidentResponseTo> findAll(Pageable restriction) {
        return repository.findAll(restriction).stream().map(mapper::toResponseTo).toList();
    }

    @Override
    public ResidentResponseTo findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntityNotFoundException(entityName, id));
    }

    @Override
    public ResidentResponseTo update(ResidentRequestTo requestTo) {
        Resident resident = repository.findById(requestTo.id())
                .orElseThrow(() -> new EntityNotFoundException(entityName, requestTo.id()));

        if (requestTo.passportId() != null) {
            Passport passportFromRequest = passportRepository.findById(requestTo.passportId())
                    .orElseThrow(() -> new EntityNotFoundException("Passport", requestTo.passportId()));
            resident.setPassport(passportFromRequest);
        }

        if (requestTo.contracts() != null) {
            List<Contract> contracts = contractRepository.findAllById(requestTo.contracts());
            resident.setContracts(contracts);
        }

        return Optional.of(repository.save(resident))
                .map(mapper::toResponseTo)
                .orElseThrow(() -> new EntitySavingException(entityName, requestTo.id()));
    }


    @Override
    public void delete(Integer id) {
        repository.findById(id)
                .ifPresentOrElse(repository::delete,
                        () -> { throw new EntityNotFoundException(entityName, id); });
    }
}
