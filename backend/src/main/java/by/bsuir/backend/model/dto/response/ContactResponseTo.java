package by.bsuir.backend.model.dto.response;

public record ContactResponseTo(
        String provider,
        String code,
        String phoneNumber,
        String email
) {
}
