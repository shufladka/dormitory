package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ResidentRequestTo(
        Integer id,

        @NotNull
        Integer passportId,

        @NotNull
        Integer balanceId,

        List<Integer> contracts
) {
}
