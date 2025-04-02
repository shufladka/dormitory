package by.bsuir.backend.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseTo(
        Integer id,
        Integer contractId,
        BigDecimal amount,
        String bankName,
        String code,
        LocalDateTime createdAt
) {
}
