package site.renzoproject.employee_service.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.dto.AttendanceSummaryDto;
import site.renzoproject.employee_service.model.Attendance;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AttendanceMapperTest {

    private final AttendanceMapper attendanceMapper = new AttendanceMapperImpl();
    private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    @Test
    void testToEntity_FromAttendanceRequestDto() {
        // Given
        UUID employeeId = UUID.randomUUID();
        UUID scheduleId = UUID.randomUUID();
        Instant checkIn = Instant.now();
        Instant checkOut = Instant.now().plusSeconds(3600); // 1 hour later

        AttendanceRequestDto requestDto = AttendanceRequestDto.builder()
                .employeeId(employeeId)
                .scheduleId(scheduleId)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .status("PRESENT")
                .source("BIOMETRIC")
                .build();

        // When
        Attendance attendance = attendanceMapper.toEntity(requestDto);

        // Then
        assertNotNull(attendance);
        assertNull(attendance.getId()); // ID should be ignored
        assertNotNull(attendance.getEmployee());
        assertEquals(employeeId, attendance.getEmployee().getId());
        assertNotNull(attendance.getEmployeeSchedule());
        assertEquals(scheduleId, attendance.getEmployeeSchedule().getId());
        assertEquals(checkIn, attendance.getCheckIn());
        assertEquals(checkOut, attendance.getCheckOut());
        assertEquals("PRESENT", attendance.getStatus());
        assertEquals("BIOMETRIC", attendance.getSource());
        assertNull(attendance.getCreatedAt()); // Audit fields should be ignored
        assertNull(attendance.getUpdatedAt());
    }

    @Test
    void testToEntity_WithNullValues() {
        // Given
        AttendanceRequestDto requestDto = AttendanceRequestDto.builder()
                .employeeId(null)
                .scheduleId(null)
                .checkIn(null)
                .checkOut(null)
                .status(null)
                .source(null)
                .build();

        // When
        Attendance attendance = attendanceMapper.toEntity(requestDto);

        // Then
        assertNotNull(attendance);
        assertNull(attendance.getEmployee());
        assertNull(attendance.getEmployeeSchedule());
        assertNull(attendance.getCheckIn());
        assertNull(attendance.getCheckOut());
        assertNull(attendance.getStatus());
        assertNull(attendance.getSource());
    }

    @Test
    void testToResponseDto_FromAttendance() {
        // Given
        UUID attendanceId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID scheduleId = UUID.randomUUID();

        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .build();

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .build();

        Instant checkIn = Instant.now();
        Instant checkOut = Instant.now().plusSeconds(3600);
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        Attendance attendance = Attendance.builder()
                .id(attendanceId)
                .employee(employee)
                .employeeSchedule(schedule)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .status("PRESENT")
                .source("BIOMETRIC")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy("admin")
                .modifiedBy("admin")
                .build();

        // When
        AttendanceResponseDto responseDto = attendanceMapper.toResponseDto(attendance, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertEquals(attendanceId, responseDto.getId());
        assertEquals(employeeId, responseDto.getEmployeeId());
        assertEquals("John Doe", responseDto.getEmployeeName());
        assertEquals(scheduleId, responseDto.getScheduleId());
        assertEquals(checkIn, responseDto.getCheckIn());
        assertEquals(checkOut, responseDto.getCheckOut());
        assertEquals("PRESENT", responseDto.getStatus());
        assertEquals("BIOMETRIC", responseDto.getSource());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(updatedAt, responseDto.getUpdatedAt());
        assertEquals("admin", responseDto.getCreatedBy());
        assertEquals("admin", responseDto.getModifiedBy());
    }

    @Test
    void testToResponseDto_WithNullEmployeeAndSchedule() {
        // Given
        Attendance attendance = Attendance.builder()
                .id(UUID.randomUUID())
                .employee(null)
                .employeeSchedule(null)
                .checkIn(Instant.now())
                .status("ABSENT")
                .source("SYSTEM")
                .build();

        // When
        AttendanceResponseDto responseDto = attendanceMapper.toResponseDto(attendance, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getEmployeeId());
        assertNull(responseDto.getEmployeeName());
        assertNull(responseDto.getScheduleId());
        assertEquals("ABSENT", responseDto.getStatus());
        assertEquals("SYSTEM", responseDto.getSource());
    }

    @Test
    void testToSummaryDto_FromAttendance() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .middleName("Marie")
                .lastName("Smith")
                .build();

        Attendance attendance = Attendance.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .checkIn(Instant.now())
                .checkOut(Instant.now().plusSeconds(7200)) // 2 hours later
                .status("LATE")
                .build();

        // When
        AttendanceSummaryDto summaryDto = attendanceMapper.toSummaryDto(attendance, employeeMapper);

        // Then
        assertNotNull(summaryDto);
        assertEquals(attendance.getId(), summaryDto.getId());
        assertEquals(employee.getId(), summaryDto.getEmployeeId());
        assertEquals("Jane Marie Smith", summaryDto.getEmployeeName());
        assertEquals(attendance.getCheckIn(), summaryDto.getCheckIn());
        assertEquals(attendance.getCheckOut(), summaryDto.getCheckOut());
        assertEquals("LATE", summaryDto.getStatus());
    }

    @Test
    void testToResponseDtoList_FromAttendanceList() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Alice")
                .lastName("Johnson")
                .build();

        Attendance attendance1 = Attendance.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .checkIn(Instant.now())
                .status("PRESENT")
                .build();

        Attendance attendance2 = Attendance.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .checkIn(Instant.now().plusSeconds(3600))
                .status("HALF_DAY")
                .build();

        List<Attendance> attendances = List.of(attendance1, attendance2);

        // When
        List<AttendanceResponseDto> responseDtos = attendanceMapper.toResponseDtoList(attendances, employeeMapper);

        // Then
        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());

        AttendanceResponseDto dto1 = responseDtos.get(0);
        assertEquals(attendance1.getId(), dto1.getId());
        assertEquals("Alice Johnson", dto1.getEmployeeName());
        assertEquals("PRESENT", dto1.getStatus());

        AttendanceResponseDto dto2 = responseDtos.get(1);
        assertEquals(attendance2.getId(), dto2.getId());
        assertEquals("Alice Johnson", dto2.getEmployeeName());
        assertEquals("HALF_DAY", dto2.getStatus());
    }

    @Test
    void testToResponseDtoList_WithNullList() {
        // When
        List<AttendanceResponseDto> responseDtos = attendanceMapper.toResponseDtoList(null, employeeMapper);

        // Then
        assertNull(responseDtos);
    }

    @Test
    void testUpdateEntityFromDto() {
        // Given
        UUID existingAttendanceId = UUID.randomUUID();
        LocalDateTime existingCreatedAt = LocalDateTime.now().minusDays(1);

        Attendance existingAttendance = Attendance.builder()
                .id(existingAttendanceId)
                .employee(Employee.builder().id(UUID.randomUUID()).build())
                .checkIn(Instant.now().minusSeconds(7200))
                .status("PRESENT")
                .source("MANUAL")
                .createdAt(existingCreatedAt)
                .createdBy("oldUser")
                .build();

        UUID newEmployeeId = UUID.randomUUID();
        UUID newScheduleId = UUID.randomUUID();
        Instant newCheckIn = Instant.now();
        Instant newCheckOut = Instant.now().plusSeconds(3600);

        AttendanceRequestDto updateDto = AttendanceRequestDto.builder()
                .employeeId(newEmployeeId)
                .scheduleId(newScheduleId)
                .checkIn(newCheckIn)
                .checkOut(newCheckOut)
                .status("LATE")
                .source("BIOMETRIC")
                .build();

        // When
        attendanceMapper.updateEntityFromDto(updateDto, existingAttendance);

        // Then
        // ID and audit fields should remain unchanged
        assertEquals(existingAttendanceId, existingAttendance.getId());
        assertEquals(existingCreatedAt, existingAttendance.getCreatedAt());
        assertEquals("oldUser", existingAttendance.getCreatedBy());

        // Other fields should be updated
        assertNotNull(existingAttendance.getEmployee());
        assertEquals(newEmployeeId, existingAttendance.getEmployee().getId());
        assertNotNull(existingAttendance.getEmployeeSchedule());
        assertEquals(newScheduleId, existingAttendance.getEmployeeSchedule().getId());
        assertEquals(newCheckIn, existingAttendance.getCheckIn());
        assertEquals(newCheckOut, existingAttendance.getCheckOut());
        assertEquals("LATE", existingAttendance.getStatus());
        assertEquals("BIOMETRIC", existingAttendance.getSource());
    }

    @Test
    void testGetEmployeeFullName_HelperMethod() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .middleName("Robert")
                .lastName("Doe")
                .nameSuffix("Jr.")
                .build();

        // When
        String fullName = attendanceMapper.getEmployeeFullName(employee, employeeMapper);

        // Then
        assertEquals("John Robert Doe Jr.", fullName);
    }

    @Test
    void testGetEmployeeFullName_WithNullEmployee() {
        // When
        String fullName = attendanceMapper.getEmployeeFullName(null, employeeMapper);

        // Then
        assertNull(fullName);
    }

    @Test
    void testGetEmployeeFullName_WithNullEmployeeMapper() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        // When
        String fullName = attendanceMapper.getEmployeeFullName(employee, null);

        // Then
        assertNull(fullName);
    }

    @Test
    void testMapEmployeeIdToEmployee_HelperMethod() {
        // Given
        UUID employeeId = UUID.randomUUID();

        // When
        Employee employee = attendanceMapper.mapEmployeeIdToEmployee(employeeId);

        // Then
        assertNotNull(employee);
        assertEquals(employeeId, employee.getId());
        assertNull(employee.getFirstName()); // Only ID should be set
        assertNull(employee.getLastName());
    }

    @Test
    void testMapEmployeeIdToEmployee_WithNullId() {
        // When
        Employee employee = attendanceMapper.mapEmployeeIdToEmployee(null);

        // Then
        assertNull(employee);
    }

    @Test
    void testMapScheduleIdToEmployeeSchedule_HelperMethod() {
        // Given
        UUID scheduleId = UUID.randomUUID();

        // When
        EmployeeSchedule schedule = attendanceMapper.mapScheduleIdToEmployeeSchedule(scheduleId);

        // Then
        assertNotNull(schedule);
        assertEquals(scheduleId, schedule.getId());
        assertNull(schedule.getDescription()); // Only ID should be set
    }
}