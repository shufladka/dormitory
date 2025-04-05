package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PassportRequestTo(
        Integer id,

        @NotNull
        @Size(min = 2, max = 40, message = "Surname must be between 2 and 40 characters.")
        String surname,

        @NotNull
        @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters.")
        String name,

        @Size(min = 2, max = 20, message = "Patronymic must be between 2 and 20 characters.")
        String patronymic,

        @NotNull
        LocalDate birthDate,

        Integer addressId,
        Integer contactId,
        Integer accountId
) {
}
