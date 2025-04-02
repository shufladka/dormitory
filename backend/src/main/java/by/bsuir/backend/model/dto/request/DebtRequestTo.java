package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DebtRequestTo(
        Integer id,

        @NotNull
        BigDecimal amount,

        @NotNull
        Integer contractId,

        @NotNull
        Integer statusId
) {
}
