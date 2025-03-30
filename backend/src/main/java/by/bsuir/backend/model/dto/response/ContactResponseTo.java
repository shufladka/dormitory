package by.bsuir.backend.model.dto.response;

public record ContactResponseTo(
        Integer id,
        String provider,
        String code,
        String phoneNumber,
        String email
) {
}
