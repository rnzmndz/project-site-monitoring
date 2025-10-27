package site.renzoproject.employee_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "/api/v1/employee-schedule-assignments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee Schedule Assignment", description = "APIs for managing employee schedule assignments")
@SecurityRequirement(name = "bearerAuth") // <-- Optional: only include if secured
public class EmployeeScheduleAssignmentController {

    private final EmployeeScheduleAssignmentService assignmentService;

    // -------------------------------------------------------------------------
    // CREATE
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Create a new employee schedule assignment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Employee schedule assignment creation request",
                    content = @Content(
                            schema = @Schema(implementation = EmployeeScheduleAssignmentRequestDto.class),
                            examples = @ExampleObject(value = """
                {
                  "employeeId": "a2f8b1f6-0b75-4c0c-a6b9-7f2b9df7ef2a",
                  "scheduleId": "b4a7a6a0-56f0-4a44-a7a4-2d9c8b3f9f7b",
                  "role": "Trainer"
                }
                """)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee schedule assignment created successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Assignment already exists")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeScheduleAssignmentResponseDto> createAssignment(
            @Valid @RequestBody EmployeeScheduleAssignmentRequestDto requestDto) {

        log.info("Creating new assignment for employeeId={}", requestDto.getEmployeeId());
        EmployeeScheduleAssignmentResponseDto response = assignmentService.createAssignment(requestDto);
        log.debug("Successfully created assignment with id={}", response.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // -------------------------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------------------------
    @Operation(summary = "Get employee schedule assignment by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee schedule assignment found",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeScheduleAssignmentResponseDto> getAssignmentById(
            @Parameter(description = "ID of the assignment to retrieve", in = ParameterIn.PATH)
            @PathVariable UUID id) {

        log.debug("Fetching assignment with id={}", id);
        EmployeeScheduleAssignmentResponseDto response = assignmentService.getAssignmentById(id);
        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------------------------
    @Operation(summary = "Get all employee schedule assignments")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all assignments")
    @GetMapping
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAllAssignments() {
        log.debug("Fetching all employee schedule assignments");
        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAllAssignments();
        log.debug("Found {} assignments", responses.size());
        return ResponseEntity.ok(responses);
    }

    // -------------------------------------------------------------------------
    // UPDATE
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Update an existing employee schedule assignment",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Updated assignment details",
                    content = @Content(
                            schema = @Schema(implementation = EmployeeScheduleAssignmentRequestDto.class),
                            examples = @ExampleObject(value = """
                {
                  "employeeId": "a2f8b1f6-0b75-4c0c-a6b9-7f2b9df7ef2a",
                  "scheduleId": "b4a7a6a0-56f0-4a44-a7a4-2d9c8b3f9f7b",
                  "role": "Trainee"
                }
                """)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee schedule assignment updated successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleAssignmentResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Assignment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeScheduleAssignmentResponseDto> updateAssignment(
            @Parameter(description = "ID of the assignment to update", in = ParameterIn.PATH)
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeScheduleAssignmentRequestDto requestDto) {

        log.info("Updating assignment with id={}", id);
        EmployeeScheduleAssignmentResponseDto response = assignmentService.updateAssignment(id, requestDto);
        return ResponseEntity.ok(response);
    }

    // -------------------------------------------------------------------------
    // DELETE
    // -------------------------------------------------------------------------
    @Operation(summary = "Delete an employee schedule assignment")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Employee schedule assignment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Assignment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(
            @Parameter(description = "ID of the assignment to delete", in = ParameterIn.PATH)
            @PathVariable UUID id) {

        log.info("Deleting assignment with id={}", id);
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------------------------------------------------------------
    // FILTERS
    // -------------------------------------------------------------------------
    @Operation(summary = "Get assignments by employee ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments for employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAssignmentsByEmployeeId(
            @Parameter(description = "ID of the employee", in = ParameterIn.PATH)
            @PathVariable UUID employeeId) {

        log.debug("Fetching assignments for employeeId={}", employeeId);
        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAssignmentsByEmployeeId(employeeId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get assignments by schedule ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments for schedule")
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAssignmentsByScheduleId(
            @Parameter(description = "ID of the schedule", in = ParameterIn.PATH)
            @PathVariable UUID scheduleId) {

        log.debug("Fetching assignments for scheduleId={}", scheduleId);
        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAssignmentsByScheduleId(scheduleId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get assignments by role")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved assignments for role")
    @GetMapping("/role/{role}")
    public ResponseEntity<List<EmployeeScheduleAssignmentResponseDto>> getAssignmentsByRole(
            @Parameter(description = "Role to filter by (Trainer, Trainee, Shift Worker)", in = ParameterIn.PATH)
            @PathVariable String role) {

        log.debug("Fetching assignments for role={}", role);
        List<EmployeeScheduleAssignmentResponseDto> responses = assignmentService.getAssignmentsByRole(role);
        return ResponseEntity.ok(responses);
    }

    // -------------------------------------------------------------------------
    // EXISTS CHECKS
    // -------------------------------------------------------------------------
    @Operation(summary = "Check if assignment exists by ID")
    @ApiResponse(responseCode = "200", description = "Successfully checked assignment existence")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkAssignmentExists(
            @Parameter(description = "ID of the assignment to check", in = ParameterIn.PATH)
            @PathVariable UUID id) {

        log.debug("Checking if assignment exists with id={}", id);
        boolean exists = assignmentService.assignmentExists(id);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Check if assignment exists for employee and schedule")
    @ApiResponse(responseCode = "200", description = "Successfully checked assignment existence")
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkAssignmentExistsForEmployeeAndSchedule(
            @Parameter(description = "Employee ID", in = ParameterIn.QUERY) @RequestParam UUID employeeId,
            @Parameter(description = "Schedule ID", in = ParameterIn.QUERY) @RequestParam UUID scheduleId) {

        log.debug("Checking assignment existence for employeeId={} and scheduleId={}", employeeId, scheduleId);
        boolean exists = assignmentService.assignmentExistsForEmployeeAndSchedule(employeeId, scheduleId);
        return ResponseEntity.ok(exists);
    }
}
