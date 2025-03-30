package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.request.LoginRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import by.bsuir.backend.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController extends AbstractController {
    
    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseTo save(@RequestBody @Valid AccountRequestTo entity) {
        return service.save(entity);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> login(@RequestBody LoginRequestTo entity) {
        boolean isAuthenticated = service.authorize(entity.username(), entity.password());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseTo> findAll(@RequestParam(defaultValue = "0") Integer pageNumber,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(defaultValue = "id,desc") String[] sortParameters) {

        List<Sort.Order> sortOrders = getSortOrderList(sortParameters);
        Pageable restriction = PageRequest.of(pageNumber, pageSize, Sort.by(sortOrders));

        return service.findAll(restriction);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseTo update(@RequestBody @Valid AccountRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
