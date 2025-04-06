package by.bsuir.backend.controller;

import by.bsuir.backend.model.dto.request.BlockRequestTo;
import by.bsuir.backend.model.dto.response.BlockResponseTo;
import by.bsuir.backend.service.BlockService;
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
@RequestMapping("/blocks")
public class BlockController extends AbstractController {
    
    private final BlockService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlockResponseTo save(@RequestBody BlockRequestTo entity) {
        return service.save(entity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BlockResponseTo> findAll() {
        return service.findAll(Pageable.unpaged());
    }

    @GetMapping("/dormitory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BlockResponseTo> findAllByDormitoryId(@PathVariable Integer id) {
        return service.findAllByDormitoryId(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BlockResponseTo findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public BlockResponseTo update(@RequestBody @Valid BlockRequestTo entity) {
        return service.update(entity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Valid Integer id) {
        service.delete(id);
    }
}
