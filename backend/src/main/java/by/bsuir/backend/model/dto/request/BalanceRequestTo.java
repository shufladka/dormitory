package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BalanceRequestTo(
        Integer id,

        @NotNull
        BigDecimal amount
) {
}
