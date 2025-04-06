package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.AccountRequestTo;
import by.bsuir.backend.model.dto.response.AccountResponseTo;
import by.bsuir.backend.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController extends AbstractController {
    
    private final AccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseTo save(@RequestBody @Valid AccountRequestTo entity) {
        return service.save(entity);
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> login(@RequestBody @Valid AccountRequestTo entity) {
        Optional<AccountResponseTo> account = service.authorize(entity.username(), entity.password());

        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccountResponseTo> findAll() {
        return service.findAll(Pageable.unpaged());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseTo update(@RequestBody AccountRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
