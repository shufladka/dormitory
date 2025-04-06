package by.bsuir.backend.model.dto.response;

public record DormitoryResponseTo(
        Integer id,
        Integer addressId,
        String dormitoryType,
        Integer floors,
        Integer blocks,
        Boolean hasLift
) {
}
