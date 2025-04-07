package by.bsuir.backend.model.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddressRequestTo(
        Integer id,

        Boolean isCity,

        @NotNull
        @Size(min = 2, max = 30, message = "Settlement must be between 2 and 30 characters.")
        String settlement,

        @NotNull
        @Size(min = 2, max = 60, message = "Street must be between 2 and 60 characters.")
        String street,

        @NotNull
        Integer buildingNumber,

        @Size(min = 0, max = 4, message = "Building index must be between 0 and 4 characters.")
        String buildingIndex,

        Integer flatNumber,

        @NotNull
        @Size(min = 2, max = 8, message = "Zipcode must be between 2 and 8 characters.")
        String zip
) {
}
