package by.bsuir.backend.model.dto.response;

public record EmployeeResponseTo(
        Integer id,
        Integer passportId,
        Integer accountId,
        String position
) {
}
