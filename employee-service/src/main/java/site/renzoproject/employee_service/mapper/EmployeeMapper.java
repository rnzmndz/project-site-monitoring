package site.renzoproject.employee_service.mapper;

import org.mapstruct.*;
import site.renzoproject.employee_service.dto.EmployeeRequestDto;
import site.renzoproject.employee_service.dto.EmployeeResponseDto;
import site.renzoproject.employee_service.model.Employee;

import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployeeMapper {

    // Request DTO to Entity (for creation)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scheduleAssignments", ignore = true)
    @Mapping(target = "leaves", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    Employee toEntity(EmployeeRequestDto employeeRequestDto);

    // Entity to Response DTO
    EmployeeResponseDto toResponseDto(Employee employee);

    // Update Entity from Request DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scheduleAssignments", ignore = true)
    @Mapping(target = "leaves", ignore = true)
    @Mapping(target = "attendances", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void updateEntityFromDto(EmployeeRequestDto employeeRequestDto, @MappingTarget Employee employee);

    // Helper method to generate full name (can be used in other mappers)
    default String getFullName(Employee employee) {
        if (employee == null) {
            return null;
        }

        StringBuilder fullName = new StringBuilder();

        if (employee.getFirstName() != null) {
            fullName.append(employee.getFirstName());
        }

        if (employee.getMiddleName() != null && !employee.getMiddleName().trim().isEmpty()) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(employee.getMiddleName());
        }

        if (employee.getLastName() != null) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(employee.getLastName());
        }

        if (employee.getNameSuffix() != null && !employee.getNameSuffix().trim().isEmpty()) {
            if (!fullName.isEmpty()) fullName.append(" ");
            fullName.append(employee.getNameSuffix());
        }

        return fullName.toString().trim();
    }

    // Helper method to map accountId to Employee (for relationships)
    default Employee mapAccountIdToEmployee(UUID accountId) {
        if (accountId == null) {
            return null;
        }
        return Employee.builder().accountId(accountId).build();
    }

    // After mapping callback to handle complex scenarios
    @AfterMapping
    default void afterMapping(@MappingTarget Employee employee, EmployeeRequestDto requestDto) {
        // Additional business logic can be added here if needed
        // For example, setting default values or validations
    }
}
