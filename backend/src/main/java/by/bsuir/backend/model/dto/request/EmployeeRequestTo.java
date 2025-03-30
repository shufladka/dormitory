package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EmployeeRequestTo(
        Integer id,

        @NotNull
        Integer passportId,

        @NotNull
        Integer positionId
) {
}
