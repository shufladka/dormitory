package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.ContactRequestTo;
import by.bsuir.backend.model.dto.response.ContactResponseTo;
import by.bsuir.backend.service.ContactService;
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
@RequestMapping("/contacts")
public class ContactController extends AbstractController {
    
    private final ContactService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactResponseTo save(@RequestBody @Valid ContactRequestTo entity) {
        return service.save(entity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ContactResponseTo> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(defaultValue = "id,desc") String[] sortParameters) {

        List<Sort.Order> sortOrders = getSortOrderList(sortParameters);
        Pageable restriction = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrders));

        return service.findAll(restriction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/passport/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ContactResponseTo findByPassportId(@PathVariable Integer id) {
        return service.findByPassportId(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ContactResponseTo update(@RequestBody @Valid ContactRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
