package site.renzoproject.employee_service.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.LeaveRequestDto;
import site.renzoproject.employee_service.dto.LeaveResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.Leave;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LeaveMapperTest {

    private final LeaveMapper leaveMapper = new LeaveMapperImpl();
    private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    @Test
    void testToEntity_FromLeaveRequestDto() {
        // Given
        UUID employeeId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        LocalDate endDate = LocalDate.of(2024, 6, 5);

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(employeeId)
                .leaveType("VACATION")
                .startDate(startDate)
                .endDate(endDate)
                .status("APPROVED")
                .reason("Annual vacation leave")
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then
        assertNotNull(leave);
        assertNull(leave.getId()); // ID should be ignored
        assertNotNull(leave.getEmployee());
        assertEquals(employeeId, leave.getEmployee().getId());
        assertEquals("VACATION", leave.getLeaveType());
        assertEquals(startDate, leave.getStartDate());
        assertEquals(endDate, leave.getEndDate());
        assertEquals("APPROVED", leave.getStatus());
        assertEquals("Annual vacation leave", leave.getReason());

        // Audit fields should be ignored
        assertNull(leave.getCreatedAt());
        assertNull(leave.getUpdatedAt());
        assertNull(leave.getCreatedBy());
        assertNull(leave.getModifiedBy());
    }

    @Test
    void testToEntity_WithNullValues() {
        // Given
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(null)
                .leaveType(null)
                .startDate(null)
                .endDate(null)
                .status(null)
                .reason(null)
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then
        assertNotNull(leave);
        assertNull(leave.getEmployee());
        assertNull(leave.getLeaveType());
        assertNull(leave.getStartDate());
        assertNull(leave.getEndDate());
        assertEquals("REQUESTED", leave.getStatus());
        assertNull(leave.getReason());
    }

    @Test
    void testToEntity_WithDefaultStatus() {
        // Given - Status not provided
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("SICK")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .reason("Feeling unwell")
                // status is null
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then - AfterMapping should set default status
        assertEquals("REQUESTED", leave.getStatus());
    }

    @Test
    void testToEntity_WithEmptyStatus() {
        // Given - Empty status string
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("SICK")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status("") // Empty string
                .reason("Medical appointment")
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then - AfterMapping should set default status
        assertEquals("REQUESTED", leave.getStatus());
    }

    @Test
    void testToEntity_WithWhitespaceStatus() {
        // Given - Whitespace status
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("SICK")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status("   ") // Whitespace
                .reason("Medical appointment")
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then - AfterMapping should set default status
        assertEquals("REQUESTED", leave.getStatus());
    }

    @Test
    void testToResponseDto_FromLeave() {
        // Given
        UUID leaveId = UUID.randomUUID();
        UUID employeeId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2024, 7, 1);
        LocalDate endDate = LocalDate.of(2024, 7, 10);
        LocalDateTime createdAt = LocalDateTime.now().minusDays(5);
        LocalDateTime updatedAt = LocalDateTime.now();

        Employee employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .build();

        Leave leave = Leave.builder()
                .id(leaveId)
                .employee(employee)
                .leaveType("VACATION")
                .startDate(startDate)
                .endDate(endDate)
                .status("APPROVED")
                .reason("Summer vacation with family")
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy("employee")
                .modifiedBy("manager")
                .build();

        // When
        LeaveResponseDto responseDto = leaveMapper.toResponseDto(leave, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertEquals(leaveId, responseDto.getId());
        assertEquals(employeeId, responseDto.getEmployeeId());
        assertEquals("John Doe", responseDto.getEmployeeName());
        assertEquals("VACATION", responseDto.getLeaveType());
        assertEquals(startDate, responseDto.getStartDate());
        assertEquals(endDate, responseDto.getEndDate());
        assertEquals("APPROVED", responseDto.getStatus());
        assertEquals("Summer vacation with family", responseDto.getReason());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(updatedAt, responseDto.getUpdatedAt());
        assertEquals("employee", responseDto.getCreatedBy());
        assertEquals("manager", responseDto.getModifiedBy());
    }

    @Test
    void testToResponseDto_WithNullEmployee() {
        // Given
        Leave leave = Leave.builder()
                .id(UUID.randomUUID())
                .employee(null)
                .leaveType("SICK")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(1))
                .status("REQUESTED")
                .reason("Medical leave")
                .build();

        // When
        LeaveResponseDto responseDto = leaveMapper.toResponseDto(leave, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getEmployeeId());
        assertNull(responseDto.getEmployeeName());
        assertEquals("SICK", responseDto.getLeaveType());
        assertEquals("REQUESTED", responseDto.getStatus());
        assertEquals("Medical leave", responseDto.getReason());
    }

    @Test
    void testToResponseDto_WithEmployeeMissingNameParts() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Alice")
                // Missing last name
                .build();

        Leave leave = Leave.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .leaveType("MATERNITY")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(90))
                .status("APPROVED")
                .reason("Maternity leave")
                .build();

        // When
        LeaveResponseDto responseDto = leaveMapper.toResponseDto(leave, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertEquals("Alice", responseDto.getEmployeeName()); // Should handle missing last name
    }

    @Test
    void testToResponseDto_ConvenienceMethod() {
        // Given
        Leave leave = Leave.builder()
                .id(UUID.randomUUID())
                .employee(Employee.builder().firstName("Bob").lastName("Smith").build())
                .leaveType("PATERNITY")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(14))
                .status("APPROVED")
                .reason("Paternity leave")
                .build();

        // When - Using convenience method without EmployeeMapper
        LeaveResponseDto responseDto = leaveMapper.toResponseDto(leave, employeeMapper);

        // Then - Should work but employee name might be null without mapper
        assertNotNull(responseDto);
        assertEquals("PATERNITY", responseDto.getLeaveType());
        assertEquals("APPROVED", responseDto.getStatus());
        // Employee name might be null depending on implementation
    }

    @Test
    void testToResponseDtoList_FromLeaveList() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Doe")
                .build();

        Leave leave1 = Leave.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .leaveType("VACATION")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .status("APPROVED")
                .reason("Short break")
                .build();

        Leave leave2 = Leave.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .leaveType("SICK")
                .startDate(LocalDate.now().plusDays(10))
                .endDate(LocalDate.now().plusDays(11))
                .status("REQUESTED")
                .reason("Doctor appointment")
                .build();

        List<Leave> leaves = List.of(leave1, leave2);

        // When
        List<LeaveResponseDto> responseDtos = leaveMapper.toResponseDtoList(leaves, employeeMapper);

        // Then
        assertNotNull(responseDtos);
        assertEquals(2, responseDtos.size());

        LeaveResponseDto dto1 = responseDtos.get(0);
        assertEquals(leave1.getId(), dto1.getId());
        assertEquals("Jane Doe", dto1.getEmployeeName());
        assertEquals("VACATION", dto1.getLeaveType());
        assertEquals("APPROVED", dto1.getStatus());

        LeaveResponseDto dto2 = responseDtos.get(1);
        assertEquals(leave2.getId(), dto2.getId());
        assertEquals("Jane Doe", dto2.getEmployeeName());
        assertEquals("SICK", dto2.getLeaveType());
        assertEquals("REQUESTED", dto2.getStatus());
    }

    @Test
    void testToResponseDtoList_WithNullList() {
        // When
        List<LeaveResponseDto> responseDtos = leaveMapper.toResponseDtoList(null, employeeMapper);

        // Then
        assertNull(responseDtos);
    }

    @Test
    void testToResponseDtoList_WithEmptyList() {
        // Given
        List<Leave> leaves = List.of();

        // When
        List<LeaveResponseDto> responseDtos = leaveMapper.toResponseDtoList(leaves, employeeMapper);

        // Then
        assertNotNull(responseDtos);
        assertTrue(responseDtos.isEmpty());
    }

    @Test
    void testToResponseDtoList_ConvenienceMethod() {
        // Given
        List<Leave> leaves = List.of(
                Leave.builder()
                        .id(UUID.randomUUID())
                        .leaveType("VACATION")
                        .status("APPROVED")
                        .build()
        );

        // When - Using convenience method without EmployeeMapper
        List<LeaveResponseDto> responseDtos = leaveMapper.toResponseDtoList(leaves, employeeMapper);

        // Then
        assertNotNull(responseDtos);
        assertEquals(1, responseDtos.size());
    }

    @Test
    void testUpdateEntityFromDto() {
        // Given
        UUID existingLeaveId = UUID.randomUUID();
        LocalDateTime existingCreatedAt = LocalDateTime.now().minusDays(3);

        Leave existingLeave = Leave.builder()
                .id(existingLeaveId)
                .employee(Employee.builder().id(UUID.randomUUID()).build())
                .leaveType("SICK")
                .startDate(LocalDate.now().minusDays(2))
                .endDate(LocalDate.now().minusDays(1))
                .status("REQUESTED")
                .reason("Original reason")
                .createdAt(existingCreatedAt)
                .createdBy("employee")
                .build();

        UUID newEmployeeId = UUID.randomUUID();
        LocalDate newStartDate = LocalDate.now().plusDays(5);
        LocalDate newEndDate = LocalDate.now().plusDays(10);

        LeaveRequestDto updateDto = LeaveRequestDto.builder()
                .employeeId(newEmployeeId)
                .leaveType("VACATION")
                .startDate(newStartDate)
                .endDate(newEndDate)
                .status("APPROVED")
                .reason("Updated vacation plans")
                .build();

        // When
        leaveMapper.updateEntityFromDto(updateDto, existingLeave);

        // Then
        // ID and audit fields should remain unchanged
        assertEquals(existingLeaveId, existingLeave.getId());
        assertEquals(existingCreatedAt, existingLeave.getCreatedAt());
        assertEquals("employee", existingLeave.getCreatedBy());

        // Other fields should be updated
        assertNotNull(existingLeave.getEmployee());
        assertEquals(newEmployeeId, existingLeave.getEmployee().getId());
        assertEquals("VACATION", existingLeave.getLeaveType());
        assertEquals(newStartDate, existingLeave.getStartDate());
        assertEquals(newEndDate, existingLeave.getEndDate());
        assertEquals("APPROVED", existingLeave.getStatus());
        assertEquals("Updated vacation plans", existingLeave.getReason());
    }

    @Test
    void testUpdateEntityFromDto_WithPartialNullValues() {
        // Given
        Leave existingLeave = Leave.builder()
                .id(UUID.randomUUID())
                .employee(Employee.builder().id(UUID.randomUUID()).build())
                .leaveType("SICK")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .status("REQUESTED")
                .reason("Existing reason")
                .build();

        LeaveRequestDto updateDto = LeaveRequestDto.builder()
                .employeeId(null) // Null employee ID
                .leaveType("VACATION") // Updated leave type
                .startDate(null) // Null start date
                .endDate(LocalDate.now().plusDays(5)) // Updated end date
                .status("APPROVED")
                .reason(null) // Null reason
                .build();

        // When
        leaveMapper.updateEntityFromDto(updateDto, existingLeave);

        // Then
        assertNull(existingLeave.getEmployee()); // Should be set to null
        assertEquals("VACATION", existingLeave.getLeaveType());
        assertNull(existingLeave.getStartDate()); // Should be set to null
        assertEquals(LocalDate.now().plusDays(5), existingLeave.getEndDate());
        assertEquals("APPROVED", existingLeave.getStatus());
        assertNull(existingLeave.getReason()); // Should be set to null
    }

    @Test
    void testUpdateEntityFromDto_WithDefaultStatus() {
        // Given
        Leave existingLeave = Leave.builder()
                .id(UUID.randomUUID())
                .leaveType("SICK")
                .status("REQUESTED")
                .build();

        LeaveRequestDto updateDto = LeaveRequestDto.builder()
                .leaveType("VACATION")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                // status is null
                .reason("Vacation")
                .build();

        // When
        leaveMapper.updateEntityFromDto(updateDto, existingLeave);

        // Then - AfterMapping should set default status
        assertEquals("REQUESTED", existingLeave.getStatus()); // Should remain unchanged if not provided in DTO
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
        String fullName = leaveMapper.getEmployeeFullName(employee, employeeMapper);

        // Then
        assertEquals("John Robert Doe Jr.", fullName);
    }

    @Test
    void testGetEmployeeFullName_WithNullEmployee() {
        // When
        String fullName = leaveMapper.getEmployeeFullName(null, employeeMapper);

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
        String fullName = leaveMapper.getEmployeeFullName(employee, null);

        // Then
        assertNull(fullName);
    }

    @Test
    void testMapEmployeeIdToEmployee_HelperMethod() {
        // Given
        UUID employeeId = UUID.randomUUID();

        // When
        Employee employee = leaveMapper.mapEmployeeIdToEmployee(employeeId);

        // Then
        assertNotNull(employee);
        assertEquals(employeeId, employee.getId());
        assertNull(employee.getFirstName()); // Only ID should be set
        assertNull(employee.getAccountId());
    }

    @Test
    void testMapEmployeeIdToEmployee_WithNullId() {
        // When
        Employee employee = leaveMapper.mapEmployeeIdToEmployee(null);

        // Then
        assertNull(employee);
    }

    @Test
    void testDifferentLeaveTypes() {
        // Given
        String[] leaveTypes = {"VACATION", "SICK", "MATERNITY", "PATERNITY", "BEREAVEMENT", "UNPAID"};
        String[] statuses = {"REQUESTED", "APPROVED", "REJECTED", "CANCELLED"};

        for (String leaveType : leaveTypes) {
            for (String status : statuses) {
                LeaveRequestDto requestDto = LeaveRequestDto.builder()
                        .employeeId(UUID.randomUUID())
                        .leaveType(leaveType)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(3))
                        .status(status)
                        .reason(leaveType + " leave - " + status)
                        .build();

                // When
                Leave leave = leaveMapper.toEntity(requestDto);

                // Then
                assertNotNull(leave);
                assertEquals(leaveType, leave.getLeaveType());
                assertEquals(status, leave.getStatus());
                assertEquals(leaveType + " leave - " + status, leave.getReason());
            }
        }
    }

    @Test
    void testLeaveWithLongReason() {
        // Given
        String longReason = "This is a very detailed reason for the leave request. " +
                "It includes all the necessary information about why the leave is needed, " +
                "what circumstances require it, and any additional details that might be " +
                "relevant for the approval process. This could be several paragraphs long " +
                "in a real-world scenario.";

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("VACATION")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(10))
                .status("REQUESTED")
                .reason(longReason)
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then
        assertNotNull(leave);
        assertEquals(longReason, leave.getReason());
    }

    @Test
    void testLeaveDateValidation_EndDateBeforeStartDate() {
        // Given - Invalid date range
        LocalDate startDate = LocalDate.now().plusDays(5);
        LocalDate endDate = LocalDate.now(); // End before start

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("VACATION")
                .startDate(startDate)
                .endDate(endDate)
                .status("REQUESTED")
                .reason("Invalid date range")
                .build();

        // When/Then - AfterMapping should throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            Leave leave = leaveMapper.toEntity(requestDto);
        });
    }

    @Test
    void testLeaveDateValidation_ValidDateRange() {
        // Given - Valid date range
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7); // End after start

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("VACATION")
                .startDate(startDate)
                .endDate(endDate)
                .status("REQUESTED")
                .reason("Valid date range")
                .build();

        // When/Then - Should not throw exception
        assertDoesNotThrow(() -> {
            Leave leave = leaveMapper.toEntity(requestDto);
            assertNotNull(leave);
            assertEquals(startDate, leave.getStartDate());
            assertEquals(endDate, leave.getEndDate());
        });
    }

    @Test
    void testLeaveDateValidation_SameStartAndEndDate() {
        // Given - Same start and end date (single day leave)
        LocalDate sameDate = LocalDate.now();

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("SICK")
                .startDate(sameDate)
                .endDate(sameDate)
                .status("REQUESTED")
                .reason("Single day sick leave")
                .build();

        // When/Then - Should not throw exception (same date is valid)
        assertDoesNotThrow(() -> {
            Leave leave = leaveMapper.toEntity(requestDto);
            assertNotNull(leave);
            assertEquals(sameDate, leave.getStartDate());
            assertEquals(sameDate, leave.getEndDate());
        });
    }

    @Test
    void testResponseDto_AllFieldsNull() {
        // Given
        Leave leave = new Leave(); // All fields null

        // When
        LeaveResponseDto responseDto = leaveMapper.toResponseDto(leave, employeeMapper);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertNull(responseDto.getEmployeeId());
        assertNull(responseDto.getEmployeeName());
        assertNull(responseDto.getLeaveType());
        assertNull(responseDto.getStartDate());
        assertNull(responseDto.getEndDate());
        assertNull(responseDto.getStatus());
        assertNull(responseDto.getReason());
        assertNull(responseDto.getCreatedAt());
        assertNull(responseDto.getUpdatedAt());
        assertNull(responseDto.getCreatedBy());
        assertNull(responseDto.getModifiedBy());
    }

    @Test
    void testMultipleMappings_Consistency() {
        // Given
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("CONSISTENCY_TEST")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .status("APPROVED")
                .reason("Consistency test reason")
                .build();

        // When - Map multiple times to ensure consistency
        Leave leave1 = leaveMapper.toEntity(requestDto);
        Leave leave2 = leaveMapper.toEntity(requestDto);

        // Then
        assertNotNull(leave1);
        assertNotNull(leave2);
        assertEquals(leave1.getEmployee().getId(), leave2.getEmployee().getId());
        assertEquals(leave1.getLeaveType(), leave2.getLeaveType());
        assertEquals(leave1.getStartDate(), leave2.getStartDate());
        assertEquals(leave1.getEndDate(), leave2.getEndDate());
        assertEquals(leave1.getStatus(), leave2.getStatus());
        assertEquals(leave1.getReason(), leave2.getReason());

        // They should be different instances
        assertNotSame(leave1, leave2);
    }

    @Test
    void testLeaveWithPastDates() {
        // Given - Leave in the past
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now().minusDays(5);

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("VACATION")
                .startDate(startDate)
                .endDate(endDate)
                .status("APPROVED")
                .reason("Past vacation")
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then - Should handle past dates without issues
        assertNotNull(leave);
        assertEquals(startDate, leave.getStartDate());
        assertEquals(endDate, leave.getEndDate());
    }

    @Test
    void testLeaveWithFutureDates() {
        // Given - Leave in the future
        LocalDate startDate = LocalDate.now().plusDays(30);
        LocalDate endDate = LocalDate.now().plusDays(37);

        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(UUID.randomUUID())
                .leaveType("VACATION")
                .startDate(startDate)
                .endDate(endDate)
                .status("REQUESTED")
                .reason("Future vacation plans")
                .build();

        // When
        Leave leave = leaveMapper.toEntity(requestDto);

        // Then - Should handle future dates without issues
        assertNotNull(leave);
        assertEquals(startDate, leave.getStartDate());
        assertEquals(endDate, leave.getEndDate());
    }
}