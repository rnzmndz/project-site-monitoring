package site.renzoproject.employee_service.mapper;

import org.mapstruct.*;
import site.renzoproject.employee_service.dto.LeaveRequestDto;
import site.renzoproject.employee_service.dto.LeaveResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.Leave;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface LeaveMapper {

    // Request DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "mapEmployeeIdToEmployee")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    Leave toEntity(LeaveRequestDto leaveRequestDto);

    // Entity to Response DTO (uses @Context for dependency injection)
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "getEmployeeFullName")
    LeaveResponseDto toResponseDto(Leave leave, @Context EmployeeMapper employeeMapper);

    // Update Entity from Request DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    @Mapping(target = "id", ignore = true)
    @Mapping(
            target = "employee",
            source = "employeeId",
            qualifiedByName = "mapEmployeeIdToEmployee",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL // 👈 override here
    )
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void updateEntityFromDto(LeaveRequestDto leaveRequestDto, @MappingTarget Leave leave);

    // Helper method to map employeeId to Employee entity
    @Named("mapEmployeeIdToEmployee")
    default Employee mapEmployeeIdToEmployee(UUID employeeId) {
        if (employeeId == null) {
            return null;
        }
        return Employee.builder().id(employeeId).build();
    }

    // Helper method to get employee's full name
    @Named("getEmployeeFullName")
    default String getEmployeeFullName(Employee employee, @Context EmployeeMapper employeeMapper) {
        if (employee == null || employeeMapper == null) {
            return null;
        }
        return employeeMapper.getFullName(employee);
    }

    // Batch mapping method
    default List<LeaveResponseDto> toResponseDtoList(List<Leave> leaves, @Context EmployeeMapper employeeMapper) {
        if (leaves == null) {
            return null;
        }
        return leaves.stream()
                .map(leave -> toResponseDto(leave, employeeMapper))
                .collect(Collectors.toList());
    }

    // After mapping callback
    @AfterMapping
    default void afterMapping(LeaveRequestDto leaveRequestDto, @MappingTarget Leave.LeaveBuilder builder) {
        // Set default status if not provided
        if (leaveRequestDto.getStatus() == null || leaveRequestDto.getStatus().trim().isEmpty()) {
            builder.status("REQUESTED");
        }

        // Validate date range
        if (leaveRequestDto.getStartDate() != null && leaveRequestDto.getEndDate() != null) {
            if (leaveRequestDto.getEndDate().isBefore(leaveRequestDto.getStartDate())) {
                throw new IllegalArgumentException("End date cannot be before start data");
            }
        }
    }

    @AfterMapping
    default void afterUpdating(LeaveRequestDto dto, @MappingTarget Leave leave) {
        // Only set default if both DTO and entity status are null/empty
        if ((dto.getStatus() == null || dto.getStatus().trim().isEmpty()) &&
                (leave.getStatus() == null || leave.getStatus().trim().isEmpty())) {
            leave.setStatus("REQUESTED");
        }
    }
}
