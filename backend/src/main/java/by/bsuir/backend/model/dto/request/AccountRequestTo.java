package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountRequestTo(
        Integer id,

        @NotNull
        @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters.")
        String username,

        @NotNull
        @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
        String password,

        @NotNull
        @Size(min = 2, max = 32, message = "Email must be between 2 and 32 characters.")
        String email
) {
}
