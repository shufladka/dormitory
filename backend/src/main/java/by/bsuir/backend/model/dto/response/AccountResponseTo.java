package by.bsuir.backend.model.dto.response;

import java.util.List;

public record AccountResponseTo(
        Integer id,
        String username,
        List<String> roles
) {
}
