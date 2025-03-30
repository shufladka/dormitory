package by.bsuir.backend.model.dto.response;

import java.time.LocalDate;

public record PassportResponseTo(
        Integer id,
        String surname,
        String name,
        String patronymic,
        LocalDate birthDate,
        Integer addressId,
        Integer contactId,
        Integer accountId
) {
}
