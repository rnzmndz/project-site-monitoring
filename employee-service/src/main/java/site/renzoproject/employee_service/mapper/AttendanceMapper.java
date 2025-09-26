package site.renzoproject.employee_service.mapper;

import org.mapstruct.*;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.dto.AttendanceSummaryDto;
import site.renzoproject.employee_service.model.Attendance;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AttendanceMapper {

    // Request DTO to Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "mapEmployeeIdToEmployee")
    @Mapping(target = "employeeSchedule", source = "scheduleId", qualifiedByName = "mapScheduleIdToEmployeeSchedule")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    Attendance toEntity(AttendanceRequestDto attendanceRequestDto);

    // Entity to Response DTO
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "getEmployeeFullName")
    @Mapping(target = "scheduleId", source = "employeeSchedule.id")
    AttendanceResponseDto toResponseDto(Attendance attendance, @Context EmployeeMapper employeeMapper);

    // Entity to Summary DTO
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "getEmployeeFullName")
    AttendanceSummaryDto toSummaryDto(Attendance attendance, @Context EmployeeMapper employeeMapper);

    default List<AttendanceResponseDto> toResponseDtoList(List<Attendance> attendances, @Context EmployeeMapper employeeMapper) {
        if (attendances == null) return null;
        return attendances.stream()
                .map(attendance -> toResponseDto(attendance, employeeMapper))
                .collect(Collectors.toList());
    }

    // Update Entity from Request DTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", source = "employeeId", qualifiedByName = "mapEmployeeIdToEmployee")
    @Mapping(target = "employeeSchedule", source = "scheduleId", qualifiedByName = "mapScheduleIdToEmployeeSchedule")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void updateEntityFromDto(AttendanceRequestDto attendanceRequestDto, @MappingTarget Attendance attendance);

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

    // Reuse the full name method from EmployeeMapper
    @Named("getEmployeeFullName")
    default String getEmployeeFullName(Employee employee, @Context EmployeeMapper employeeMapper) {
        if (employee == null) {
            return null;
        }
        return employeeMapper.getFullName(employee);
    }
}
