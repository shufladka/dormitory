package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ContractRequestTo(
        Integer id,
        BigDecimal rentPrice,

        @NotNull
        Integer blockId,

        Integer statusId
) {
}
