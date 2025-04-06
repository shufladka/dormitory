package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.RoleRequestTo;
import by.bsuir.backend.model.dto.response.RoleResponseTo;
import by.bsuir.backend.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController extends AbstractController {
    
    private final RoleService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponseTo save(@RequestBody @Valid RoleRequestTo entity) {
        return service.save(entity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoleResponseTo> findAll() {
        return service.findAll(Pageable.unpaged());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoleResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public RoleResponseTo update(@RequestBody @Valid RoleRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
