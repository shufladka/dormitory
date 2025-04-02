package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ViolationRequestTo(
        Integer id,

        @NotNull
        Integer residentId,

        @NotNull
        Integer statusId,

        @NotNull
        String reason
) {
}
