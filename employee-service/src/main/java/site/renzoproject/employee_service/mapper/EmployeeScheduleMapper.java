package site.renzoproject.employee_service.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import site.renzoproject.employee_service.dto.EmployeeScheduleRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleResponseDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleSummaryDto;
import site.renzoproject.employee_service.model.EmployeeSchedule;

import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EmployeeScheduleAssignmentMapper.class})
public interface EmployeeScheduleMapper {

    // Request DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    EmployeeSchedule toEntity(EmployeeScheduleRequestDto employeeScheduleRequestDto);

    // Entity to Response DTO
    @Mapping(target = "assignments", source = "assignments")
    EmployeeScheduleResponseDto toResponseDto(EmployeeSchedule employeeSchedule);

    // Entity to Summary DTO
    EmployeeScheduleSummaryDto toSummaryDto(EmployeeSchedule employeeSchedule);

    // Update Entity from Request DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assignments", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void updateEntityFromDto(EmployeeScheduleRequestDto employeeScheduleRequestDto, @MappingTarget EmployeeSchedule employeeSchedule);

    // Helper method to map schedule ID to EmployeeSchedule entity
    default EmployeeSchedule mapIdToEmployeeSchedule(UUID id) {
        if (id == null) {
            return null;
        }
        return EmployeeSchedule.builder().id(id).build();
    }
}
