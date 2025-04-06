package by.bsuir.backend.model.dto.response;

import java.util.List;

public record ResidentResponseTo(
        Integer id,
        Integer passportId,
        Integer accountId,
        List<Integer> contracts
) {
}
