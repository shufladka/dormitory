package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DormitoryTypeRequestTo(
        Integer id,

        @NotNull
        @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters.")
        String name
) {
}
