package site.renzoproject.employee_service.mapper;

import org.mapstruct.*;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;

import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EmployeeMapper.class, EmployeeScheduleMapper.class})
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
    EmployeeScheduleAssignmentResponseDto toResponseDto(EmployeeScheduleAssignment assignment);

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
    default String getEmployeeFullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        // In actual usage, this would be injected by Spring
        EmployeeMapper employeeMapper = new EmployeeMapperImpl();
        return employeeMapper.getFullName(employee);
    }
}
