package site.renzoproject.employee_service.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeScheduleAssignmentMapperTest {

    private final EmployeeScheduleAssignmentMapper assignmentMapper = new EmployeeScheduleAssignmentMapperImpl();
    private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    @Test
    void testToEntity_FromEmployeeScheduleAssignmentRequestDto() {
        // Given
        UUID employeeId = UUID.randomUUID();
        UUID scheduleId = UUID.randomUUID();
        Instant assignedAt = Instant.now();

        EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(employeeId)
                .employeeScheduleId(scheduleId)
                .assignedAt(assignedAt)
                .role("Trainee")
                .build();

        // When
        EmployeeScheduleAssignment assignment = assignmentMapper.toEntity(requestDto);

        // Then
        assertNotNull(assignment);
        assertNull(assignment.getId()); // ID should be ignored
        assertNotNull(assignment.getEmployee());
        assertEquals(employeeId, assignment.getEmployee().getId());
        assertNotNull(assignment.getEmployeeSchedule());
        assertEquals(scheduleId, assignment.getEmployeeSchedule().getId());
        assertEquals(assignedAt, assignment.getAssignedAt());
        assertEquals("Trainee", assignment.getRole());

        // Audit fields should be ignored
        assertNull(assignment.getCreatedAt());
        assertNull(assignment.getUpdatedAt());
        assertNull(assignment.getCreatedBy());
        assertNull(assignment.getModifiedBy());
    }

    @Test
    void testToEntity_WithNullValues() {
        // Given
        EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(null)
                .employeeScheduleId(null)
                .assignedAt(null)
                .role(null)
                .build();

        // When
        EmployeeScheduleAssignment assignment = assignmentMapper.toEntity(requestDto);

        // Then
        assertNotNull(assignment);
        assertNull(assignment.getEmployee());
        assertNull(assignment.getEmployeeSchedule());
        assertNull(assignment.getAssignedAt());
        assertNull(assignment.getRole());
    }

    @Test
    void testToResponseDto_FromEmployeeScheduleAssignment() {
        // Given
        UUID assignmentId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        UUID scheduleId = UUID.randomUUID();

        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .build();

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Training Session")
                .build();

        Instant assignedAt = Instant.now();
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        EmployeeScheduleAssignment assignment = EmployeeScheduleAssignment.builder()
                .id(assignmentId)
                .employee(employee)
                .employeeSchedule(schedule)
                .assignedAt(assignedAt)
                .role("Trainer")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy("admin")
                .modifiedBy("admin")
                .build();

        // When
        EmployeeScheduleAssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignment, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertEquals(assignmentId, responseDto.getId());
        assertEquals(employeeId, responseDto.getEmployeeId());
        assertEquals("John Doe", responseDto.getEmployeeName());
        assertEquals(scheduleId, responseDto.getScheduleId());
        assertEquals("Morning Training Session", responseDto.getScheduleDescription());
        assertEquals(assignedAt, responseDto.getAssignedAt());
        assertEquals("Trainer", responseDto.getRole());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(updatedAt, responseDto.getUpdatedAt());
        assertEquals("admin", responseDto.getCreatedBy());
        assertEquals("admin", responseDto.getModifiedBy());
    }

    @Test
    void testToResponseDto_WithNullEmployeeAndSchedule() {
        // Given
        EmployeeScheduleAssignment assignment = EmployeeScheduleAssignment.builder()
                .id(UUID.randomUUID())
                .employee(null)
                .employeeSchedule(null)
                .assignedAt(Instant.now())
                .role("Shift Worker")
                .build();

        // When
        EmployeeScheduleAssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignment, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getEmployeeId());
        assertNull(responseDto.getEmployeeName());
        assertNull(responseDto.getScheduleId());
        assertNull(responseDto.getScheduleDescription());
        assertEquals("Shift Worker", responseDto.getRole());
    }

    @Test
    void testToResponseDto_WithEmployeeMissingNameParts() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                // Missing last name
                .build();

        EmployeeScheduleAssignment assignment = EmployeeScheduleAssignment.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .employeeSchedule(EmployeeSchedule.builder().id(UUID.randomUUID()).build())
                .assignedAt(Instant.now())
                .role("Trainee")
                .build();

        // When
        EmployeeScheduleAssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignment, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertEquals("John", responseDto.getEmployeeName()); // Should handle missing last name
    }

    @Test
    void testToResponseDtoList_FromAssignmentList() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Alice")
                .lastName("Johnson")
                .build();

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Weekend Shift")
                .build();

        EmployeeScheduleAssignment assignment1 = EmployeeScheduleAssignment.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .employeeSchedule(schedule)
                .assignedAt(Instant.now())
                .role("Shift Worker")
                .build();

        EmployeeScheduleAssignment assignment2 = EmployeeScheduleAssignment.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .employeeSchedule(schedule)
                .assignedAt(Instant.now().plusSeconds(3600))
                .role("Backup")
                .build();

        List<EmployeeScheduleAssignment> assignments = List.of(assignment1, assignment2);

        // When
        List<EmployeeScheduleAssignmentResponseDto> responseDtos =
                assignmentMapper.toResponseDtoList(assignments, employeeMapper);

        // Then
        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());

        EmployeeScheduleAssignmentResponseDto dto1 = responseDtos.get(0);
        assertEquals(assignment1.getId(), dto1.getId());
        assertEquals("Alice Johnson", dto1.getEmployeeName());
        assertEquals("Weekend Shift", dto1.getScheduleDescription());
        assertEquals("Shift Worker", dto1.getRole());

        EmployeeScheduleAssignmentResponseDto dto2 = responseDtos.get(1);
        assertEquals(assignment2.getId(), dto2.getId());
        assertEquals("Alice Johnson", dto2.getEmployeeName());
        assertEquals("Weekend Shift", dto2.getScheduleDescription());
        assertEquals("Backup", dto2.getRole());
    }

    @Test
    void testToResponseDtoList_WithNullList() {
        // When
        List<EmployeeScheduleAssignmentResponseDto> responseDtos =
                assignmentMapper.toResponseDtoList(null, employeeMapper);

        // Then
        assertNull(responseDtos);
    }

    @Test
    void testToResponseDtoList_WithEmptyList() {
        // Given
        List<EmployeeScheduleAssignment> assignments = List.of();

        // When
        List<EmployeeScheduleAssignmentResponseDto> responseDtos =
                assignmentMapper.toResponseDtoList(assignments, employeeMapper);

        // Then
        assertNotNull(responseDtos);
        assertTrue(responseDtos.isEmpty());
    }

    @Test
    void testUpdateEntityFromDto() {
        // Given
        UUID existingAssignmentId = UUID.randomUUID();
        LocalDateTime existingCreatedAt = LocalDateTime.now().minusDays(1);

        EmployeeScheduleAssignment existingAssignment = EmployeeScheduleAssignment.builder()
                .id(existingAssignmentId)
                .employee(Employee.builder().id(UUID.randomUUID()).build())
                .employeeSchedule(EmployeeSchedule.builder().id(UUID.randomUUID()).build())
                .assignedAt(Instant.now().minusSeconds(3600))
                .role("Old Role")
                .createdAt(existingCreatedAt)
                .createdBy("oldUser")
                .build();

        UUID newEmployeeId = UUID.randomUUID();
        UUID newScheduleId = UUID.randomUUID();
        Instant newAssignedAt = Instant.now();

        EmployeeScheduleAssignmentRequestDto updateDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(newEmployeeId)
                .employeeScheduleId(newScheduleId)
                .assignedAt(newAssignedAt)
                .role("New Role")
                .build();

        // When
        assignmentMapper.updateEntityFromDto(updateDto, existingAssignment);

        // Then
        // ID and audit fields should remain unchanged
        assertEquals(existingAssignmentId, existingAssignment.getId());
        assertEquals(existingCreatedAt, existingAssignment.getCreatedAt());
        assertEquals("oldUser", existingAssignment.getCreatedBy());

        // Other fields should be updated
        assertNotNull(existingAssignment.getEmployee());
        assertEquals(newEmployeeId, existingAssignment.getEmployee().getId());
        assertNotNull(existingAssignment.getEmployeeSchedule());
        assertEquals(newScheduleId, existingAssignment.getEmployeeSchedule().getId());
        assertEquals(newAssignedAt, existingAssignment.getAssignedAt());
        assertEquals("New Role", existingAssignment.getRole());
    }

    @Test
    void testUpdateEntityFromDto_WithPartialNullValues() {
        // Given
        EmployeeScheduleAssignment existingAssignment = EmployeeScheduleAssignment.builder()
                .id(UUID.randomUUID())
                .employee(Employee.builder().id(UUID.randomUUID()).build())
                .employeeSchedule(EmployeeSchedule.builder().id(UUID.randomUUID()).build())
                .assignedAt(Instant.now())
                .role("Existing Role")
                .build();

        EmployeeScheduleAssignmentRequestDto updateDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(null) // Null employee ID
                .employeeScheduleId(UUID.randomUUID()) // New schedule ID
                .assignedAt(null) // Null assigned at
                .role("Updated Role")
                .build();

        // When
        assignmentMapper.updateEntityFromDto(updateDto, existingAssignment);

        // Then
        assertNull(existingAssignment.getEmployee()); // Should be set to null
        assertNotNull(existingAssignment.getEmployeeSchedule()); // Should be updated
        assertNull(existingAssignment.getAssignedAt()); // Should be set to null
        assertEquals("Updated Role", existingAssignment.getRole());
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
        String fullName = assignmentMapper.getEmployeeFullName(employee, employeeMapper);

        // Then
        assertEquals("John Robert Doe Jr.", fullName);
    }

    @Test
    void testGetEmployeeFullName_WithNullEmployee() {
        // When
        String fullName = assignmentMapper.getEmployeeFullName(null, employeeMapper);

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
        String fullName = assignmentMapper.getEmployeeFullName(employee, null);

        // Then
        assertNull(fullName);
    }

    @Test
    void testMapEmployeeIdToEmployee_HelperMethod() {
        // Given
        UUID employeeId = UUID.randomUUID();

        // When
        Employee employee = assignmentMapper.mapEmployeeIdToEmployee(employeeId);

        // Then
        assertNotNull(employee);
        assertEquals(employeeId, employee.getId());
        assertNull(employee.getFirstName()); // Only ID should be set
        assertNull(employee.getAccountId());
    }

    @Test
    void testMapEmployeeIdToEmployee_WithNullId() {
        // When
        Employee employee = assignmentMapper.mapEmployeeIdToEmployee(null);

        // Then
        assertNull(employee);
    }

    @Test
    void testMapScheduleIdToEmployeeSchedule_HelperMethod() {
        // Given
        UUID scheduleId = UUID.randomUUID();

        // When
        EmployeeSchedule schedule = assignmentMapper.mapScheduleIdToEmployeeSchedule(scheduleId);

        // Then
        assertNotNull(schedule);
        assertEquals(scheduleId, schedule.getId());
        assertNull(schedule.getDescription()); // Only ID should be set
        assertNull(schedule.getStartTime());
    }

    @Test
    void testMapScheduleIdToEmployeeSchedule_WithNullId() {
        // When
        EmployeeSchedule schedule = assignmentMapper.mapScheduleIdToEmployeeSchedule(null);

        // Then
        assertNull(schedule);
    }

    @Test
    void testDifferentRoleTypes() {
        // Given
        String[] roles = {"Trainer", "Trainee", "Shift Worker", "Backup", "Supervisor"};

        for (String role : roles) {
            EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                    .employeeId(UUID.randomUUID())
                    .employeeScheduleId(UUID.randomUUID())
                    .assignedAt(Instant.now())
                    .role(role)
                    .build();

            // When
            EmployeeScheduleAssignment assignment = assignmentMapper.toEntity(requestDto);

            // Then
            assertNotNull(assignment);
            assertEquals(role, assignment.getRole());
        }
    }

    @Test
    void testAssignmentWithFutureAssignedAt() {
        // Given
        Instant futureAssignedAt = Instant.now().plusSeconds(86400); // 1 day in future

        EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .employeeScheduleId(UUID.randomUUID())
                .assignedAt(futureAssignedAt)
                .role("Future Assignment")
                .build();

        // When
        EmployeeScheduleAssignment assignment = assignmentMapper.toEntity(requestDto);

        // Then
        assertNotNull(assignment);
        assertEquals(futureAssignedAt, assignment.getAssignedAt());
        assertEquals("Future Assignment", assignment.getRole());
    }

    @Test
    void testAssignmentWithPastAssignedAt() {
        // Given
        Instant pastAssignedAt = Instant.now().minusSeconds(86400); // 1 day in past

        EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .employeeScheduleId(UUID.randomUUID())
                .assignedAt(pastAssignedAt)
                .role("Past Assignment")
                .build();

        // When
        EmployeeScheduleAssignment assignment = assignmentMapper.toEntity(requestDto);

        // Then
        assertNotNull(assignment);
        assertEquals(pastAssignedAt, assignment.getAssignedAt());
        assertEquals("Past Assignment", assignment.getRole());
    }

    @Test
    void testResponseDto_AllFieldsNull() {
        // Given
        EmployeeScheduleAssignment assignment = new EmployeeScheduleAssignment(); // All fields null

        // When
        EmployeeScheduleAssignmentResponseDto responseDto = assignmentMapper.toResponseDto(assignment, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertNull(responseDto.getEmployeeId());
        assertNull(responseDto.getEmployeeName());
        assertNull(responseDto.getScheduleId());
        assertNull(responseDto.getScheduleDescription());
        assertNull(responseDto.getAssignedAt());
        assertNull(responseDto.getRole());
        assertNull(responseDto.getCreatedAt());
        assertNull(responseDto.getUpdatedAt());
        assertNull(responseDto.getCreatedBy());
        assertNull(responseDto.getModifiedBy());
    }

    @Test
    void testMultipleMappings_Consistency() {
        // Given
        EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .employeeScheduleId(UUID.randomUUID())
                .assignedAt(Instant.now())
                .role("Consistency Test")
                .build();

        // When - Map multiple times to ensure consistency
        EmployeeScheduleAssignment assignment1 = assignmentMapper.toEntity(requestDto);
        EmployeeScheduleAssignment assignment2 = assignmentMapper.toEntity(requestDto);

        // Then
        assertNotNull(assignment1);
        assertNotNull(assignment2);
        assertEquals(assignment1.getEmployee().getId(), assignment2.getEmployee().getId());
        assertEquals(assignment1.getEmployeeSchedule().getId(), assignment2.getEmployeeSchedule().getId());
        assertEquals(assignment1.getAssignedAt(), assignment2.getAssignedAt());
        assertEquals(assignment1.getRole(), assignment2.getRole());

        // They should be different instances
        assertNotSame(assignment1, assignment2);
    }
}