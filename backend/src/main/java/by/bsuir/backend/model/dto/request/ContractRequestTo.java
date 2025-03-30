package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ContractRequestTo(
        Integer id,

        @NotNull
        BigDecimal rentPrice,

        @NotNull
        Integer blockId,

        @NotNull
        Integer statusId
) {
}
