package site.renzoproject.employee_service.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.repository.EmployeeRepository;
import site.renzoproject.employee_service.repository.EmployeeScheduleAssignmentRepository;
import site.renzoproject.employee_service.repository.EmployeeScheduleRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class EmployeeScheduleAssignmentServiceIntegrationTest {

    @Autowired
    private EmployeeScheduleAssignmentService assignmentService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    private EmployeeScheduleAssignmentRepository assignmentRepository;

    @Test
    void createAndRetrieveAssignment_IntegrationTest() {
        // Create test employee and schedule first
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .build();
        employee = employeeRepository.save(employee);

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Morning Shift")
                .build();
        schedule = employeeScheduleRepository.save(schedule);

        // Create assignment request
        EmployeeScheduleAssignmentRequestDto requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(employee.getId())
                .employeeScheduleId(schedule.getId())
                .assignedAt(Instant.now())
                .role("Trainer")
                .build();

        // Create assignment
        EmployeeScheduleAssignmentResponseDto response = assignmentService.createAssignment(requestDto);

        // Verify creation
        assertNotNull(response.getId());
        assertEquals(employee.getId(), response.getEmployeeId());
        assertEquals(schedule.getId(), response.getScheduleId());
        assertEquals("Trainer", response.getRole());

        // Verify retrieval
        EmployeeScheduleAssignmentResponseDto retrieved = assignmentService.getAssignmentById(response.getId());
        assertNotNull(retrieved);
        assertEquals(response.getId(), retrieved.getId());

        // Verify retrieval by employee ID
        List<EmployeeScheduleAssignmentResponseDto> employeeAssignments =
                assignmentService.getAssignmentsByEmployeeId(employee.getId());
        assertFalse(employeeAssignments.isEmpty());
        assertEquals(response.getId(), employeeAssignments.get(0).getId());

        // Verify retrieval by schedule ID
        List<EmployeeScheduleAssignmentResponseDto> scheduleAssignments =
                assignmentService.getAssignmentsByScheduleId(schedule.getId());
        assertFalse(scheduleAssignments.isEmpty());
        assertEquals(response.getId(), scheduleAssignments.get(0).getId());
    }
}
