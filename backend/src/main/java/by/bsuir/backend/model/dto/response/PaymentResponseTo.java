package by.bsuir.backend.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseTo(
        Integer id,
        Integer balanceId,
        BigDecimal amount,
        String bankName,
        String code,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt
) {
}
