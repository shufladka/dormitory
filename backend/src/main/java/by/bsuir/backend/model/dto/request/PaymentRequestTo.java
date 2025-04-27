package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequestTo(
        Integer id,

        @NotNull
        Integer balanceId,

        @NotNull
        BigDecimal amount,

        @NotNull
        String bankName,

        @NotNull
        String code
) {
}
