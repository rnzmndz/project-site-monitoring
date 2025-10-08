package site.renzoproject.employee_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.service.EmployeeScheduleAssignmentService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee-schedule-assignments")
@Tag(name = "Employee Schedule Assignment", description = "APIs for managing employee schedule assignments")
public class EmployeeScheduleAssignmentController {

    private final EmployeeScheduleAssignmentService assignmentService;

    @Operation(summary = "Create a new employee schedule assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee schedule assignment created successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Assignment already exists")
    })
    @PostMapping
    public ResponseEntity<EmployeeScheduleAssignmentResponseDto> createAssignment(
            @Valid @RequestBody EmployeeScheduleAssignmentRequestDto requestDto) {
        log.info("POST /api/v1/employee-schedule-assignments - Creating new assignment for employee: {}",
                requestDto.getEmployeeId());

        EmployeeScheduleAssignmentResponseDto response = assignmentService.createAssignment(requestDto);
        log.debug("Successfully created assignment with id: {}", response.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get employee schedule assignment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee schedule assignment found",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeScheduleAssignmentResponseDto> getAssignmentById(
            @Parameter(description = "ID of the assignment to retrieve")
            @PathVariable UUID id) {
        log.debug("GET /api/v1/employee-schedule-assignments/{} - Fetching assignment", id);

        EmployeeScheduleAssignmentResponseDto response = assignmentService.getAssignmentById(id);
        log.debug("Successfully fetched assignment with id: {}", id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all employee schedule assignments")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all assignments")
    @GetMapping
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAllAssignments() {
        log.debug("GET /api/v1/employee-schedule-assignments - Fetching all assignments");

        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAllAssignments();
        log.debug("Successfully fetched {} assignments", responses.size());

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update an existing employee schedule assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee schedule assignment updated successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeScheduleAssignmentResponseDto> updateAssignment(
            @Parameter(description = "ID of the assignment to update")
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeScheduleAssignmentRequestDto requestDto) {
        log.info("PUT /api/v1/employee-schedule-assignments/{} - Updating assignment", id);

        EmployeeScheduleAssignmentResponseDto response = assignmentService.updateAssignment(id, requestDto);
        log.debug("Successfully updated assignment with id: {}", id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an employee schedule assignment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee schedule assignment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @Parameter(description = "ID of the assignment to delete")
            @PathVariable UUID id) {
        log.info("DELETE /api/v1/employee-schedule-assignments/{} - Deleting assignment", id);

        assignmentService.deleteAssignment(id);
        log.debug("Successfully deleted assignment with id: {}", id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get assignments by employee ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments for employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAssignmentsByEmployeeId(
            @Parameter(description = "ID of the employee")
            @PathVariable UUID employeeId) {
        log.debug("GET /api/v1/employee-schedule-assignments/employee/{} - Fetching assignments for employee", employeeId);

        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAssignmentsByEmployeeId(employeeId);
        log.debug("Successfully fetched {} assignments for employeeId: {}", responses.size(), employeeId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get assignments by schedule ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments for schedule")
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAssignmentsByScheduleId(
            @Parameter(description = "ID of the schedule")
            @PathVariable UUID scheduleId) {
        log.debug("GET /api/v1/employee-schedule-assignments/schedule/{} - Fetching assignments for schedule", scheduleId);

        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAssignmentsByScheduleId(scheduleId);
        log.debug("Successfully fetched {} assignments for scheduleId: {}", responses.size(), scheduleId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get assignments by role")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments for role")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAssignmentsByRole(
            @Parameter(description = "Role to filter by (Trainer, Trainee, Shift Worker)")
            @PathVariable String role) {
        log.debug("GET /api/v1/employee-schedule-assignments/role/{} - Fetching assignments for role", role);

        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAssignmentsByRole(role);
        log.debug("Successfully fetched {} assignments for role: {}", responses.size(), role);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Check if assignment exists")
    @ApiResponse(responseCode = "200", description = "Successfully checked assignment existence")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkAssignmentExists(
            @Parameter(description = "ID of the assignment to check")
            @PathVariable UUID id) {
        log.debug("GET /api/v1/employee-schedule-assignments/{}/exists - Checking assignment existence", id);

        boolean exists = assignmentService.assignmentExists(id);
        log.debug("Assignment with id {} exists: {}", id, exists);

        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Check if assignment exists for employee and schedule")
    @ApiResponse(responseCode = "200", description = "Successfully checked assignment existence")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkAssignmentExistsForEmployeeAndSchedule(
            @Parameter(description = "Employee ID") @RequestParam UUID employeeId,
            @Parameter(description = "Schedule ID") @RequestParam UUID scheduleId) {
        log.debug("GET /api/v1/employee-schedule-assignments/exists?employeeId={}&scheduleId={} - Checking assignment existence",
                employeeId, scheduleId);

        boolean exists = assignmentService.assignmentExistsForEmployeeAndSchedule(employeeId, scheduleId);
        log.debug("Assignment exists for employeeId {} and scheduleId {}: {}", employeeId, scheduleId, exists);

        return ResponseEntity.ok(exists);
    }
}
