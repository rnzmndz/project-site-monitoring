package site.renzoproject.employee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.renzoproject.employee_service.dto.EmployeePage;
import site.renzoproject.employee_service.dto.EmployeeRequestDto;
import site.renzoproject.employee_service.dto.EmployeeResponseDto;
import site.renzoproject.employee_service.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Create a new employee
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        log.info("POST /api/v1/employees - Creating new employee for account: {}",
                employeeRequestDto.getAccountId());

        EmployeeResponseDto createdEmployee = employeeService.createEmployee(employeeRequestDto);

        log.info("Successfully created employee with ID: {}", createdEmployee.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    /**
     * Get all employees with pagination
     */
    @GetMapping
    public ResponseEntity<EmployeePage> getAllEmployees(
            @PageableDefault(size = 20, sort = "lastName,firstName") Pageable pageable) {
        log.info("GET /api/v1/employees - Fetching all employees with pageable: {}", pageable);

        EmployeePage employeePage = employeeService.getAllEmployees(pageable);

        log.info("Returning {} employees out of {}",
                employeePage.getContent().size(), employeePage.getTotalElements());
        return ResponseEntity.ok(employeePage);
    }

    /**
     * Get employee by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable UUID id) {
        log.info("GET /api/v1/employees/{} - Fetching employee by ID", id);

        EmployeeResponseDto employee = employeeService.getEmployeeById(id);

        log.info("Successfully found employee with ID: {}", id);
        return ResponseEntity.ok(employee);
    }

    /**
     * Get employee by account ID
     */
    @GetMapping("/account/{accountId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByAccountId(@PathVariable UUID accountId) {
        log.info("GET /api/v1/employees/account/{} - Fetching employee by account ID", accountId);

        EmployeeResponseDto employee = employeeService.getEmployeeByAccountId(accountId);

        log.info("Successfully found employee with account ID: {}", accountId);
        return ResponseEntity.ok(employee);
    }

    /**
     * Update employee (full update)
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        log.info("PUT /api/v1/employees/{} - Updating employee", id);

        EmployeeResponseDto updatedEmployee = employeeService.updateEmployee(id, employeeRequestDto);

        log.info("Successfully updated employee with ID: {}", id);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * Partial update employee
     */
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> partialUpdateEmployee(
            @PathVariable UUID id,
            @RequestBody EmployeeRequestDto employeeRequestDto) {
        log.info("PATCH /api/v1/employees/{} - Partially updating employee", id);

        EmployeeResponseDto updatedEmployee = employeeService.partialUpdateEmployee(id, employeeRequestDto);

        log.info("Successfully partially updated employee with ID: {}", id);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * Delete employee
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        log.info("DELETE /api/v1/employees/{} - Deleting employee", id);

        employeeService.deleteEmployee(id);

        log.info("Successfully deleted employee with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if employee exists by ID
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID id) {
        log.debug("GET /api/v1/employees/{}/exists - Checking employee existence", id);

        boolean exists = employeeService.existsById(id);

        return ResponseEntity.ok(exists);
    }

    /**
     * Check if employee exists by account ID
     */
    @GetMapping("/account/{accountId}/exists")
    public ResponseEntity<Boolean> existsByAccountId(@PathVariable UUID accountId) {
        log.debug("GET /api/v1/employees/account/{}/exists - Checking employee existence by account ID", accountId);

        boolean exists = employeeService.existsByAccountId(accountId);

        return ResponseEntity.ok(exists);
    }

    /**
     * Search employees by department
     */
    @GetMapping("/search/department")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDepartment(
            @RequestParam String department) {
        log.info("GET /api/v1/employees/search/department?department={} - Searching employees by department", department);

        List<EmployeeResponseDto> employees = employeeService.getEmployeesByDepartment(department);

        log.info("Found {} employees in department: {}", employees.size(), department);
        return ResponseEntity.ok(employees);
    }

    /**
     * Search employees by job title
     */
    @GetMapping("/search/job-title")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByJobTitle(
            @RequestParam String jobTitle) {
        log.info("GET /api/v1/employees/search/job-title?jobTitle={} - Searching employees by job title", jobTitle);

        List<EmployeeResponseDto> employees = employeeService.getEmployeesByJobTitle(jobTitle);

        log.info("Found {} employees with job title: {}", employees.size(), jobTitle);
        return ResponseEntity.ok(employees);
    }

    /**
     * Get employees hired after a specific date
     */
    @GetMapping("/search/hired-after")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesHiredAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("GET /api/v1/employees/search/hired-after?date={} - Searching employees hired after date", date);

        List<EmployeeResponseDto> employees = employeeService.getEmployeesHiredAfter(date);

        log.info("Found {} employees hired after: {}", employees.size(), date);
        return ResponseEntity.ok(employees);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.debug("GET /api/v1/employees/health - Health check");
        return ResponseEntity.ok("Employee Service is healthy");
    }
}
