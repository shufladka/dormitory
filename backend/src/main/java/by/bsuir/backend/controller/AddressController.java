package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.AddressRequestTo;
import by.bsuir.backend.model.dto.response.AddressResponseTo;
import by.bsuir.backend.service.AddressService;
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
@RequestMapping("/addresses")
public class AddressController extends AbstractController {
    
    private final AddressService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponseTo save(@RequestBody @Valid AddressRequestTo entity) {
        return service.save(entity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AddressResponseTo> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(defaultValue = "id,desc") String[] sortParameters) {

        List<Sort.Order> sortOrders = getSortOrderList(sortParameters);
        Pageable restriction = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrders));

        return service.findAll(restriction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/passport/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseTo findByPassportId(@PathVariable Integer id) {
        return service.findByPassportId(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AddressResponseTo update(@RequestBody @Valid AddressRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
