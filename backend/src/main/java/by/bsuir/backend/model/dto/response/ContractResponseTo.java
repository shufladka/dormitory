package by.bsuir.backend.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContractResponseTo(
        Integer id,
        Integer blockId,
        Integer statusId,
        BigDecimal rentPrice,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
