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
import site.renzoproject.employee_service.dto.*;
import site.renzoproject.employee_service.service.EmployeeScheduleService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee-schedules")
public class EmployeeScheduleController {

    private final EmployeeScheduleService employeeScheduleService;

    // CREATE operations

    /**
     * Create a new employee schedule
     */
    @PostMapping
    public ResponseEntity<EmployeeScheduleResponseDto> createEmployeeSchedule(
            @Valid @RequestBody EmployeeScheduleRequestDto requestDto) {
        log.info("POST /api/v1/employee-schedules - Creating new employee schedule: {}",
                requestDto.getDescription());

        EmployeeScheduleResponseDto createdSchedule = employeeScheduleService.createEmployeeSchedule(requestDto);

        log.info("Successfully created employee schedule with ID: {}", createdSchedule.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    /**
     * Bulk create employee schedules
     */
    @PostMapping("/bulk")
    public ResponseEntity<Void> bulkCreateEmployeeSchedules(
            @Valid @RequestBody List<EmployeeScheduleRequestDto> requestDtos) {
        log.info("POST /api/v1/employee-schedules/bulk - Bulk creating {} employee schedules",
                requestDtos.size());

        employeeScheduleService.bulkCreateEmployeeSchedules(requestDtos);

        log.info("Successfully bulk created {} employee schedules", requestDtos.size());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // READ operations

    /**
     * Get employee schedule by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeScheduleResponseDto> getEmployeeScheduleById(@PathVariable UUID id) {
        log.info("GET /api/v1/employee-schedules/{} - Fetching employee schedule by ID", id);

        EmployeeScheduleResponseDto schedule = employeeScheduleService.getEmployeeScheduleById(id);

        log.info("Successfully found employee schedule with ID: {}", id);
        return ResponseEntity.ok(schedule);
    }

    /**
     * Get all employee schedules with pagination
     */
    @GetMapping
    public ResponseEntity<EmployeeSchedulePage> getAllEmployeeSchedules(
            @PageableDefault(size = 20, sort = "startTime") Pageable pageable) {
        log.info("GET /api/v1/employee-schedules - Fetching all employee schedules with pagination: {}",
                pageable);

        EmployeeSchedulePage schedulePage = employeeScheduleService.getAllEmployeeSchedules(pageable);

        log.info("Returning {} employee schedules out of {}",
                schedulePage.getContent().size(), schedulePage.getTotalElements());
        return ResponseEntity.ok(schedulePage);
    }

    /**
     * Get all employee schedules (non-paginated)
     */
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getAllEmployeeSchedules() {
        log.info("GET /api/v1/employee-schedules/all - Fetching all employee schedules");

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getAllEmployeeSchedules();

        log.info("Returning {} employee schedules", schedules.size());
        return ResponseEntity.ok(schedules);
    }

    /**
     * Get employee schedules by type
     */
    @GetMapping("/type/{scheduleType}")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesByType(
            @PathVariable String scheduleType) {
        log.info("GET /api/v1/employee-schedules/type/{} - Fetching employee schedules by type", scheduleType);

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getEmployeeSchedulesByType(scheduleType);

        log.info("Returning {} employee schedules with type: {}", schedules.size(), scheduleType);
        return ResponseEntity.ok(schedules);
    }

    /**
     * Get employee schedules by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesByStatus(
            @PathVariable String status) {
        log.info("GET /api/v1/employee-schedules/status/{} - Fetching employee schedules by status", status);

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getEmployeeSchedulesByStatus(status);

        log.info("Returning {} employee schedules with status: {}", schedules.size(), status);
        return ResponseEntity.ok(schedules);
    }

    /**
     * Get employee schedules by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("GET /api/v1/employee-schedules/date-range - Fetching employee schedules from {} to {}",
                startDate, endDate);

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getEmployeeSchedulesByDateRange(startDate, endDate);

        log.info("Returning {} employee schedules between {} and {}", schedules.size(), startDate, endDate);
        return ResponseEntity.ok(schedules);
    }

    /**
     * Get employee schedules that overlap with time range
     */
    @GetMapping("/overlapping")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getEmployeeSchedulesOverlapping(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endTime) {
        log.info("GET /api/v1/employee-schedules/overlapping - Fetching employee schedules overlapping with {} to {}",
                startTime, endTime);

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getEmployeeSchedulesOverlapping(startTime, endTime);

        log.info("Returning {} employee schedules overlapping with the time range", schedules.size());
        return ResponseEntity.ok(schedules);
    }

    /**
     * Get active employee schedules (not cancelled)
     */
    @GetMapping("/active")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getActiveEmployeeSchedules() {
        log.info("GET /api/v1/employee-schedules/active - Fetching active employee schedules");

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getActiveEmployeeSchedules();

        log.info("Returning {} active employee schedules", schedules.size());
        return ResponseEntity.ok(schedules);
    }

    /**
     * Get employee schedules summary
     */
    @GetMapping("/summary")
    public ResponseEntity<List<EmployeeScheduleSummaryDto>> getEmployeeSchedulesSummary() {
        log.info("GET /api/v1/employee-schedules/summary - Fetching employee schedules summary");

        List<EmployeeScheduleSummaryDto> summary = employeeScheduleService.getEmployeeSchedulesSummary();

        log.info("Returning employee schedules summary with {} records", summary.size());
        return ResponseEntity.ok(summary);
    }

    /**
     * Get employee schedules summary by type
     */
    @GetMapping("/summary/type/{scheduleType}")
    public ResponseEntity<List<EmployeeScheduleSummaryDto>> getEmployeeSchedulesSummaryByType(
            @PathVariable String scheduleType) {
        log.info("GET /api/v1/employee-schedules/summary/type/{} - Fetching employee schedules summary by type",
                scheduleType);

        List<EmployeeScheduleSummaryDto> summary = employeeScheduleService.getEmployeeSchedulesSummaryByType(scheduleType);

        log.info("Returning employee schedules summary with {} records for type: {}", summary.size(), scheduleType);
        return ResponseEntity.ok(summary);
    }

    /**
     * Get upcoming employee schedules
     */
    @GetMapping("/upcoming")
    public ResponseEntity<List<EmployeeScheduleResponseDto>> getUpcomingEmployeeSchedules(
            @RequestParam(defaultValue = "7") int days) {
        log.info("GET /api/v1/employee-schedules/upcoming - Fetching upcoming employee schedules for next {} days",
                days);

        List<EmployeeScheduleResponseDto> schedules = employeeScheduleService.getUpcomingEmployeeSchedules(days);

        log.info("Returning {} upcoming employee schedules for next {} days", schedules.size(), days);
        return ResponseEntity.ok(schedules);
    }

    // UPDATE operations

    /**
     * Update employee schedule
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeScheduleResponseDto> updateEmployeeSchedule(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeScheduleRequestDto requestDto) {
        log.info("PUT /api/v1/employee-schedules/{} - Updating employee schedule", id);

        EmployeeScheduleResponseDto updatedSchedule = employeeScheduleService.updateEmployeeSchedule(id, requestDto);

        log.info("Successfully updated employee schedule with ID: {}", id);
        return ResponseEntity.ok(updatedSchedule);
    }

    /**
     * Update employee schedule status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<EmployeeScheduleResponseDto> updateEmployeeScheduleStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        log.info("PATCH /api/v1/employee-schedules/{}/status - Updating employee schedule status to: {}",
                id, status);

        EmployeeScheduleResponseDto updatedSchedule = employeeScheduleService.updateEmployeeScheduleStatus(id, status);

        log.info("Successfully updated status for employee schedule ID: {}", id);
        return ResponseEntity.ok(updatedSchedule);
    }

    /**
     * Approve employee schedule
     */
    @PatchMapping("/{id}/approve")
    public ResponseEntity<EmployeeScheduleResponseDto> approveEmployeeSchedule(@PathVariable UUID id) {
        log.info("PATCH /api/v1/employee-schedules/{}/approve - Approving employee schedule", id);

        EmployeeScheduleResponseDto approvedSchedule = employeeScheduleService.approveEmployeeSchedule(id);

        log.info("Successfully approved employee schedule with ID: {}", id);
        return ResponseEntity.ok(approvedSchedule);
    }

    /**
     * Cancel employee schedule
     */
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<EmployeeScheduleResponseDto> cancelEmployeeSchedule(
            @PathVariable UUID id,
            @RequestParam(required = false) String reason) {
        log.info("PATCH /api/v1/employee-schedules/{}/cancel - Cancelling employee schedule", id);

        EmployeeScheduleResponseDto cancelledSchedule = employeeScheduleService.cancelEmployeeSchedule(id, reason);

        log.info("Successfully cancelled employee schedule with ID: {}", id);
        return ResponseEntity.ok(cancelledSchedule);
    }

    /**
     * Reschedule employee schedule
     */
    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<EmployeeScheduleResponseDto> rescheduleEmployeeSchedule(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant newStartTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant newEndTime) {
        log.info("PATCH /api/v1/employee-schedules/{}/reschedule - Rescheduling employee schedule to {} - {}",
                id, newStartTime, newEndTime);

        EmployeeScheduleResponseDto rescheduledSchedule = employeeScheduleService.rescheduleEmployeeSchedule(id, newStartTime, newEndTime);

        log.info("Successfully rescheduled employee schedule with ID: {}", id);
        return ResponseEntity.ok(rescheduledSchedule);
    }

    // DELETE operations

    /**
     * Delete employee schedule
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeSchedule(@PathVariable UUID id) {
        log.info("DELETE /api/v1/employee-schedules/{} - Deleting employee schedule", id);

        employeeScheduleService.deleteEmployeeSchedule(id);

        log.info("Successfully deleted employee schedule with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete employee schedules by status
     */
    @DeleteMapping("/status/{status}")
    public ResponseEntity<Long> deleteEmployeeSchedulesByStatus(@PathVariable String status) {
        log.info("DELETE /api/v1/employee-schedules/status/{} - Deleting employee schedules by status", status);

        long deletedCount = employeeScheduleService.deleteEmployeeSchedulesByStatus(status);

        log.info("Successfully deleted {} employee schedules with status: {}", deletedCount, status);
        return ResponseEntity.ok(deletedCount);
    }

    // UTILITY endpoints

    /**
     * Check if employee schedule exists by ID
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID id) {
        log.debug("GET /api/v1/employee-schedules/{}/exists - Checking employee schedule existence", id);

        boolean exists = employeeScheduleService.existsById(id);

        return ResponseEntity.ok(exists);
    }

    /**
     * Get total employee schedule count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getEmployeeScheduleCount() {
        log.debug("GET /api/v1/employee-schedules/count - Getting total employee schedule count");

        long count = employeeScheduleService.getEmployeeScheduleCount();

        return ResponseEntity.ok(count);
    }

    /**
     * Get employee schedule count by status
     */
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getEmployeeScheduleCountByStatus(@PathVariable String status) {
        log.debug("GET /api/v1/employee-schedules/count/status/{} - Getting employee schedule count by status", status);

        long count = employeeScheduleService.getEmployeeScheduleCountByStatus(status);

        return ResponseEntity.ok(count);
    }

    /**
     * Get employee schedule count by type
     */
    @GetMapping("/count/type/{scheduleType}")
    public ResponseEntity<Long> getEmployeeScheduleCountByType(@PathVariable String scheduleType) {
        log.debug("GET /api/v1/employee-schedules/count/type/{} - Getting employee schedule count by type", scheduleType);

        long count = employeeScheduleService.getEmployeeScheduleCountByType(scheduleType);

        return ResponseEntity.ok(count);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.debug("GET /api/v1/employee-schedules/health - Health check");
        return ResponseEntity.ok("Employee Schedule Service is healthy");
    }
}
