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
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.renzoproject.employee_service.dto.*;
import site.renzoproject.employee_service.service.EmployeeScheduleService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/employee-schedules", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Employee Schedule", description = "APIs for managing employee schedules")
@SecurityRequirement(name = "bearerAuth") // Remove this if not using JWT
public class EmployeeScheduleController {

    private final EmployeeScheduleService employeeScheduleService;

    // -------------------------------------------------------------------------
    // CREATE operations
    // -------------------------------------------------------------------------
    @Operation(
            summary = "Create a new employee schedule",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Employee schedule creation request",
                    content = @Content(
                            schema = @Schema(implementation = EmployeeScheduleRequestDto.class),
                            examples = @ExampleObject(value = """
                {
                  "description": "Morning shift - Team A",
                  "startTime": "2025-11-01T08:00:00Z",
                  "endTime": "2025-11-01T16:00:00Z",
                  "type": "SHIFT",
                  "status": "ACTIVE"
                }
                """)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee schedule created successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeScheduleResponseDto> createEmployeeSchedule(
            @Valid @RequestBody EmployeeScheduleRequestDto requestDto) {

        log.info("Creating new employee schedule: {}", requestDto.getDescription());
        EmployeeScheduleResponseDto created = employeeScheduleService.createEmployeeSchedule(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Bulk create employee schedules")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employee schedules created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping(value = "/bulk", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> bulkCreateEmployeeSchedules(
            @Valid @RequestBody List<EmployeeScheduleRequestDto> requestDtos) {

        log.info("Bulk creating {} employee schedules", requestDtos.size());
        employeeScheduleService.bulkCreateEmployeeSchedules(requestDtos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // -------------------------------------------------------------------------
    // READ operations
    // -------------------------------------------------------------------------
    @Operation(summary = "Get employee schedule by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employee schedule found",
                    content = @Content(schema = @Schema(implementation = EmployeeScheduleResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Employee schedule not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeScheduleResponseDto> getEmployeeScheduleById(
            @Parameter(description = "ID of the schedule to retrieve", in = ParameterIn.PATH)
            @PathVariable UUID id) {

        EmployeeScheduleResponseDto schedule = employeeScheduleService.getEmployeeScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    @Operation(summary = "Get all employee schedules with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated employee schedules")
    @GetMapping
    public ResponseEntity<EmployeeSchedulePage> getAllEmployeeSchedules(
            @Parameter(description = "Pagination and sorting parameters")
            @PageableDefault(size = 20, sort = "startTime") Pageable pageable) {

        EmployeeSchedulePage page = employeeScheduleService.getAllEmployeeSchedules(pageable);
        return ResponseEntity.ok(page);
    }

    @Operation(summary = "Get all employee schedules (non-paginated)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all employee schedules")
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getAllEmployeeSchedules() {
        return ResponseEntity.ok(employeeScheduleService.getAllEmployeeSchedules());
    }

    @Operation(summary = "Get employee schedules by type")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved schedules by type")
    @GetMapping("/type/{scheduleType}")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesByType(
            @Parameter(description = "Type of the schedule") @PathVariable String scheduleType) {

        return ResponseEntity.ok(employeeScheduleService.getEmployeeSchedulesByType(scheduleType));
    }

    @Operation(summary = "Get employee schedules by status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved schedules by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesByStatus(
            @Parameter(description = "Status of the schedule") @PathVariable String status) {

        return ResponseEntity.ok(employeeScheduleService.getEmployeeSchedulesByStatus(status));
    }

    @Operation(summary = "Get schedules within a date range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved schedules within date range")
    @GetMapping("/date-range")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(employeeScheduleService.getEmployeeSchedulesByDateRange(startDate, endDate));
    }

    @Operation(summary = "Get schedules overlapping with a time range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved overlapping schedules")
    @GetMapping("/overlapping")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesOverlapping(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {

        return ResponseEntity.ok(employeeScheduleService.getEmployeeSchedulesOverlapping(startTime, endTime));
    }

    @Operation(summary = "Get all active (non-cancelled) schedules")
    @GetMapping("/active")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getActiveEmployeeSchedules() {
        return ResponseEntity.ok(employeeScheduleService.getActiveEmployeeSchedules());
    }

    @Operation(summary = "Get summary of all employee schedules")
    @GetMapping("/summary")
    public ResponseEntity<List<EmployeeScheduleSummaryDto>> getEmployeeSchedulesSummary() {
        return ResponseEntity.ok(employeeScheduleService.getEmployeeSchedulesSummary());
    }

    @Operation(summary = "Get summary of employee schedules by type")
    @GetMapping("/summary/type/{scheduleType}")
    public ResponseEntity<List<EmployeeScheduleSummaryDto>> getEmployeeSchedulesSummaryByType(
            @Parameter(description = "Type of the schedule") @PathVariable String scheduleType) {

        return ResponseEntity.ok(employeeScheduleService.getEmployeeSchedulesSummaryByType(scheduleType));
    }

    @Operation(summary = "Get upcoming employee schedules for the next N days")
    @GetMapping("/upcoming")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getUpcomingEmployeeSchedules(
            @Parameter(description = "Number of days to look ahead (default: 7)")
            @RequestParam(defaultValue = "7") int days) {

        return ResponseEntity.ok(employeeScheduleService.getUpcomingEmployeeSchedules(days));
    }

    // -------------------------------------------------------------------------
    // UPDATE operations
    // -------------------------------------------------------------------------
    @Operation(summary = "Update an existing employee schedule")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeScheduleResponseDto> updateEmployeeSchedule(
            @Parameter(description = "ID of the schedule to update") @PathVariable UUID id,
            @Valid @RequestBody EmployeeScheduleRequestDto requestDto) {

        return ResponseEntity.ok(employeeScheduleService.updateEmployeeSchedule(id, requestDto));
    }

    @Operation(summary = "Update employee schedule status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeScheduleResponseDto> updateEmployeeScheduleStatus(
            @Parameter(description = "ID of the schedule to update") @PathVariable UUID id,
            @RequestParam String status) {

        return ResponseEntity.ok(employeeScheduleService.updateEmployeeScheduleStatus(id, status));
    }

    @Operation(summary = "Approve an employee schedule")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<EmployeeScheduleResponseDto> approveEmployeeSchedule(
            @Parameter(description = "ID of the schedule to approve") @PathVariable UUID id) {

        return ResponseEntity.ok(employeeScheduleService.approveEmployeeSchedule(id));
    }

    @Operation(summary = "Cancel an employee schedule")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<EmployeeScheduleResponseDto> cancelEmployeeSchedule(
            @Parameter(description = "ID of the schedule to cancel") @PathVariable UUID id,
            @RequestParam(required = false) String reason) {

        return ResponseEntity.ok(employeeScheduleService.cancelEmployeeSchedule(id, reason));
    }

    @Operation(summary = "Reschedule an employee schedule")
    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<EmployeeScheduleResponseDto> rescheduleEmployeeSchedule(
            @Parameter(description = "ID of the schedule to reschedule") @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant newStartTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant newEndTime) {

        return ResponseEntity.ok(employeeScheduleService.rescheduleEmployeeSchedule(id, newStartTime, newEndTime));
    }

    // -------------------------------------------------------------------------
    // DELETE operations
    // -------------------------------------------------------------------------
    @Operation(summary = "Delete employee schedule by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeSchedule(@PathVariable UUID id) {
        employeeScheduleService.deleteEmployeeSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete employee schedules by status")
    @DeleteMapping("/status/{status}")
    public ResponseEntity<Long> deleteEmployeeSchedulesByStatus(@PathVariable String status) {
        long deleted = employeeScheduleService.deleteEmployeeSchedulesByStatus(status);
        return ResponseEntity.ok(deleted);
    }

    // -------------------------------------------------------------------------
    // UTILITY endpoints
    // -------------------------------------------------------------------------
    @Operation(summary = "Check if an employee schedule exists by ID")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID id) {
        return ResponseEntity.ok(employeeScheduleService.existsById(id));
    }

    @Operation(summary = "Get total employee schedule count")
    @GetMapping("/count")
    public ResponseEntity<Long> getEmployeeScheduleCount() {
        return ResponseEntity.ok(employeeScheduleService.getEmployeeScheduleCount());
    }

    @Operation(summary = "Get employee schedule count by status")
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getEmployeeScheduleCountByStatus(@PathVariable String status) {
        return ResponseEntity.ok(employeeScheduleService.getEmployeeScheduleCountByStatus(status));
    }

    @Operation(summary = "Get employee schedule count by type")
    @GetMapping("/count/type/{scheduleType}")
    public ResponseEntity<Long> getEmployeeScheduleCountByType(@PathVariable String scheduleType) {
        return ResponseEntity.ok(employeeScheduleService.getEmployeeScheduleCountByType(scheduleType));
    }

    @Operation(summary = "Health check endpoint")
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Employee Schedule Service is healthy");
    }
}
