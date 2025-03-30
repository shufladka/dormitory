package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DormitoryRequestTo(
        Integer id,

        @NotNull
        Integer addressId,

        @NotNull
        Integer dormitoryTypeId,

        @NotNull
        Integer floors,

        @NotNull
        Integer blocks,

        @NotNull
        Boolean hasLift
) {
}
