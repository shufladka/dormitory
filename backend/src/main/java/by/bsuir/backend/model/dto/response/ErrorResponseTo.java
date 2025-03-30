package by.bsuir.backend.model.dto.response;

public record ErrorResponseTo(
        String errorMessage,
        Integer errorCode
) {
}
