package site.renzoproject.employee_service.mapper;

import org.mapstruct.*;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployeeScheduleAssignmentMapper {

    // Request DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "mapEmployeeIdToEmployee")
    @Mapping(target = "employeeSchedule", source = "employeeScheduleId", qualifiedByName = "mapScheduleIdToEmployeeSchedule")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    EmployeeScheduleAssignment toEntity(EmployeeScheduleAssignmentRequestDto requestDto);

    // Entity to Response DTO
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "getEmployeeFullName")
    @Mapping(target = "scheduleId", source = "employeeSchedule.id")
    @Mapping(target = "scheduleDescription", source = "employeeSchedule.description")
    EmployeeScheduleAssignmentResponseDto toResponseDto(EmployeeScheduleAssignment assignment, @Context EmployeeMapper employeeMapper);

    default EmployeeScheduleAssignmentResponseDto toResponseDto(EmployeeScheduleAssignment assignment) {
        return toResponseDto(assignment, null);
    }

    default List<EmployeeScheduleAssignmentResponseDto> toResponseDtoList(
            List<EmployeeScheduleAssignment> assignments, @Context EmployeeMapper employeeMapper) {
        if (assignments == null) return null;
        return assignments.stream()
                .map(assignment -> toResponseDto(assignment, employeeMapper))
                .collect(Collectors.toList());
    }

    // Update Entity from Request DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "mapEmployeeIdToEmployee")
    @Mapping(target = "employeeSchedule", source = "employeeScheduleId", qualifiedByName = "mapScheduleIdToEmployeeSchedule")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void updateEntityFromDto(EmployeeScheduleAssignmentRequestDto requestDto, @MappingTarget EmployeeScheduleAssignment assignment);

    // Helper method to map employeeId to Employee entity
    @Named("mapEmployeeIdToEmployee")
    default Employee mapEmployeeIdToEmployee(UUID employeeId) {
        if (employeeId == null) {
            return null;
        }
        return Employee.builder().id(employeeId).build();
    }

    // Helper method to map scheduleId to EmployeeSchedule entity
    @Named("mapScheduleIdToEmployeeSchedule")
    default EmployeeSchedule mapScheduleIdToEmployeeSchedule(UUID scheduleId) {
        if (scheduleId == null) {
            return null;
        }
        return EmployeeSchedule.builder().id(scheduleId).build();
    }

    // Helper method to get employee full name (reusing from EmployeeMapper)
    @Named("getEmployeeFullName")
    default String getEmployeeFullName(Employee employee, @Context EmployeeMapper employeeMapper) {
        if (employee == null) {
            return null;
        }
        return employeeMapper.getFullName(employee);
    }
}
