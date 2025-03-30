package by.bsuir.backend.model.dto.response;

public record DormitoryResponseTo(
        Integer id,
        Integer addressId,
        Integer dormitoryTypeId,
        Integer floors,
        Integer blocks,
        Boolean hasLift
) {
}
