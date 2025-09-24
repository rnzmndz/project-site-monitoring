package site.renzoproject.employee_service.mapper;

import org.mapstruct.*;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.dto.AttendanceSummaryDto;
import site.renzoproject.employee_service.model.Attendance;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;

import java.util.UUID;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {EmployeeMapper.class, EmployeeScheduleMapper.class})
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
    AttendanceResponseDto toResponseDto(Attendance attendance);

    // Entity to Summary DTO
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeName", source = "employee", qualifiedByName = "getEmployeeFullName")
    AttendanceSummaryDto toSummaryDto(Attendance attendance);

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
    default String getEmployeeFullName(Employee employee) {
        if (employee == null) {
            return null;
        }
        EmployeeMapper employeeMapper = new EmployeeMapperImpl(); // This will be injected by Spring in actual usage
        return employeeMapper.getFullName(employee);
    }
}
