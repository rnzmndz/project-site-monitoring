package site.renzoproject.employee_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.employee_service.dto.LeaveRequestDto;
import site.renzoproject.employee_service.dto.LeaveResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.repository.EmployeeRepository;
import site.renzoproject.employee_service.repository.LeaveRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LeaveServiceIntegrationTest {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void createAndRetrieveLeave_ShouldWorkCorrectly() {
        // Arrange
        Employee employee = createTestEmployee();
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(employee.getId())
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .reason("Integration test")
                .build();

        // Act
        LeaveResponseDto createdLeave = leaveService.createLeave(requestDto);
        LeaveResponseDto retrievedLeave = leaveService.getLeaveById(createdLeave.getId());

        // Assert
        assertNotNull(createdLeave.getId());
        assertEquals(employee.getId(), createdLeave.getEmployeeId());
        assertEquals("VACATION", createdLeave.getLeaveType());
        assertEquals("REQUESTED", createdLeave.getStatus());
        assertEquals(createdLeave.getId(), retrievedLeave.getId());
        assertEquals(createdLeave.getEmployeeId(), retrievedLeave.getEmployeeId());
    }

    @Test
    void updateLeaveStatus_ShouldUpdateCorrectly() {
        // Arrange
        Employee employee = createTestEmployee();
        LeaveRequestDto requestDto = LeaveRequestDto.builder()
                .employeeId(employee.getId())
                .leaveType("SICK")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(3))
                .reason("Sick leave")
                .build();

        LeaveResponseDto createdLeave = leaveService.createLeave(requestDto);

        // Act
        LeaveResponseDto updatedLeave = leaveService.updateLeaveStatus(createdLeave.getId(), "APPROVED");

        // Assert
        assertEquals("APPROVED", updatedLeave.getStatus());
        assertEquals(createdLeave.getId(), updatedLeave.getId());
    }

    @Test
    void getLeavesByEmployeeId_ShouldReturnCorrectLeaves() {
        // Arrange
        Employee employee1 = createTestEmployee();
        Employee employee2 = createTestEmployee();

        LeaveRequestDto request1 = LeaveRequestDto.builder()
                .employeeId(employee1.getId())
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .reason("Employee 1 vacation")
                .build();

        LeaveRequestDto request2 = LeaveRequestDto.builder()
                .employeeId(employee2.getId())
                .leaveType("SICK")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(4))
                .reason("Employee 2 sick")
                .build();

        leaveService.createLeave(request1);
        leaveService.createLeave(request2);

        // Act
        List<LeaveResponseDto> employee1Leaves = leaveService.getLeavesByEmployeeId(employee1.getId());

        // Assert
        assertEquals(1, employee1Leaves.size());
        assertEquals(employee1.getId(), employee1Leaves.get(0).getEmployeeId());
        assertEquals("Employee 1 vacation", employee1Leaves.get(0).getReason());
    }

    private Employee createTestEmployee() {
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Test")
                .lastName("Employee")
                .build();
        return employeeRepository.save(employee);
    }
}
