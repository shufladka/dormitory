package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record BlockRequestTo(
        Integer id,

        @NotNull
        Integer dormitoryId,

        @NotNull
        Integer roomNumber,

        @NotNull
        Boolean isGasified,

        @NotNull
        Boolean isBathroomSeparated,

        @NotNull
        Integer capacity,

        @NotNull
        Integer floor
) {
}
