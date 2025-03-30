package by.bsuir.backend.model.dto.response;

public record AddressResponseTo(
        Integer id,
        Boolean isCity,
        String settlement,
        String street,
        Integer buildingNumber,
        String buildingIndex,
        Integer flatNumber,
        String zip
) {
}
