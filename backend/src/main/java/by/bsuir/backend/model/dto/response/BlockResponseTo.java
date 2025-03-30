package by.bsuir.backend.model.dto.response;

public record BlockResponseTo(
        Integer id,
        Integer dormitoryId,
        Integer roomNumber,
        Boolean isGasified,
        Boolean isBathroomSeparated,
        Integer capacity,
        Integer floor
) {
}
