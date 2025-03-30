package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ContactRequestTo(
        Integer id,

        @NotNull
        @Size(min = 2, max = 20, message = "Provider must be between 2 and 20 characters.")
        String provider,

        @NotNull
        @Size(min = 2, max = 10, message = "Code must be between 2 and 10 characters.")
        String code,

        @NotNull
        @Size(min = 2, max = 7, message = "Phone number must be between 2 and 7 characters.")
        String phoneNumber,

        @NotNull
        @Size(min = 2, max = 32, message = "Email must be between 2 and 32 characters.")
        String email
) {
}
