package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.PassportRequestTo;
import by.bsuir.backend.model.dto.response.PassportResponseTo;
import by.bsuir.backend.service.PassportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/passports")
public class PassportController extends AbstractController {
    
    private final PassportService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassportResponseTo save(@RequestBody PassportRequestTo entity) {
        return service.save(entity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PassportResponseTo> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(defaultValue = "id,desc") String[] sortParameters) {

        List<Sort.Order> sortOrders = getSortOrderList(sortParameters);
        Pageable restriction = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrders));

        return service.findAll(restriction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PassportResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PassportResponseTo update(@RequestBody @Valid PassportRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
