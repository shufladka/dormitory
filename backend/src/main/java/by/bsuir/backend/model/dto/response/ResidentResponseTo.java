package by.bsuir.backend.model.dto.response;

import java.util.List;

public record ResidentResponseTo(
        Integer id,
        Integer passportId,
        List<Integer> contracts
) {
}
