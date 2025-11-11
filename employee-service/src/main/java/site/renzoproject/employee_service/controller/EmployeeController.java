package site.renzoproject.employee_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Employee Management", description = "APIs for managing employees, their accounts, and employment details.")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(
            summary = "Create a new employee",
            description = "Registers a new employee record and returns the created employee details.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created successfully",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        log.info("POST /api/v1/employees - Creating new employee for account: {}", employeeRequestDto.getAccountId());
        EmployeeResponseDto createdEmployee = employeeService.createEmployee(employeeRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @Operation(
            summary = "Get all employees (paginated)",
            description = "Retrieves all employees with pagination and sorting support.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based)", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
                    @Parameter(name = "size", description = "Page size", in = ParameterIn.QUERY, schema = @Schema(type = "integer")),
                    @Parameter(name = "sort", description = "Sort by property, e.g. lastName,asc", in = ParameterIn.QUERY, schema = @Schema(type = "string"))
            },
            responses = @ApiResponse(responseCode = "200", description = "List of employees",
                    content = @Content(schema = @Schema(implementation = EmployeePage.class)))
    )
    @GetMapping
    public ResponseEntity<EmployeePage> getAllEmployees(
            @PageableDefault(size = 20, sort = "lastName,firstName") Pageable pageable) {
        EmployeePage employeePage = employeeService.getAllEmployees(pageable);
        return ResponseEntity.ok(employeePage);
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Fetches detailed information of a specific employee by their ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee found",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable UUID id) {
        EmployeeResponseDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @Operation(
            summary = "Get employee by Account ID",
            description = "Fetches the employee record associated with a specific account.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee found",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @GetMapping("/account/{accountId}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeByAccountId(@PathVariable UUID accountId) {
        EmployeeResponseDto employee = employeeService.getEmployeeByAccountId(accountId);
        return ResponseEntity.ok(employee);
    }

    @Operation(
            summary = "Update employee (full update)",
            description = "Updates all fields of an employee record.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto updatedEmployee = employeeService.updateEmployee(id, employeeRequestDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(
            summary = "Partial update employee",
            description = "Partially updates an employee’s record (only provided fields are changed).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee partially updated successfully",
                            content = @Content(schema = @Schema(implementation = EmployeeResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> partialUpdateEmployee(
            @PathVariable UUID id,
            @RequestBody EmployeeRequestDto employeeRequestDto) {
        EmployeeResponseDto updatedEmployee = employeeService.partialUpdateEmployee(id, employeeRequestDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(
            summary = "Delete employee",
            description = "Deletes an employee record by ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if employee exists by ID")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeService.existsById(id));
    }

    @Operation(summary = "Check if employee exists by Account ID")
    @GetMapping("/account/{accountId}/exists")
    public ResponseEntity<Boolean> existsByAccountId(@PathVariable UUID accountId) {
        return ResponseEntity.ok(employeeService.existsByAccountId(accountId));
    }

    @Operation(summary = "Search employees by department")
    @GetMapping("/search/department")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDepartment(
            @RequestParam String department) {
        List<EmployeeResponseDto> employees = employeeService.getEmployeesByDepartment(department);
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Search employees by job title")
    @GetMapping("/search/job-title")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByJobTitle(
            @RequestParam String jobTitle) {
        List<EmployeeResponseDto> employees = employeeService.getEmployeesByJobTitle(jobTitle);
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get employees hired after a specific date")
    @GetMapping("/search/hired-after")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesHiredAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<EmployeeResponseDto> employees = employeeService.getEmployeesHiredAfter(date);
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Health check", description = "Simple endpoint to verify that the Employee Service is running.")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Employee Service is healthy");
    }
}
