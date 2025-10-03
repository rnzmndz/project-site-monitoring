package site.renzoproject.employee_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.dto.AttendanceSummaryDto;
import site.renzoproject.employee_service.service.AttendanceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // CREATE operations

    /**
     * Create a new attendance record
     */
    @PostMapping
    public ResponseEntity<AttendanceResponseDto> createAttendance(
            @Valid @RequestBody AttendanceRequestDto requestDto) {
        log.info("POST /api/v1/attendances - Creating new attendance record for employee: {}",
                requestDto.getEmployeeId());

        AttendanceResponseDto createdAttendance = attendanceService.createAttendance(requestDto);

        log.info("Successfully created attendance record with ID: {}", createdAttendance.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttendance);
    }

    /**
     * Bulk create attendance records
     */
    @PostMapping("/bulk")
    public ResponseEntity<Void> bulkCreateAttendances(
            @Valid @RequestBody List<AttendanceRequestDto> requestDtos) {
        log.info("POST /api/v1/attendances/bulk - Bulk creating {} attendance records", requestDtos.size());

        attendanceService.bulkCreateAttendances(requestDtos);

        log.info("Successfully bulk created {} attendance records", requestDtos.size());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Check-in for an employee
     */
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponseDto> checkIn(
            @RequestParam UUID employeeId,
            @RequestParam(required = false) UUID scheduleId,
            @RequestParam(required = false) String source) {
        log.info("POST /api/v1/attendances/check-in - Check-in for employee: {}, schedule: {}",
                employeeId, scheduleId);

        AttendanceResponseDto attendance = attendanceService.checkIn(employeeId, scheduleId, source);

        log.info("Successfully recorded check-in for employee: {}", employeeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }

    /**
     * Check-out for an employee
     */
    @PostMapping("/check-out")
    public ResponseEntity<AttendanceResponseDto> checkOut(
            @RequestParam UUID employeeId,
            @RequestParam(required = false) String source) {
        log.info("POST /api/v1/attendances/check-out - Check-out for employee: {}", employeeId);

        AttendanceResponseDto attendance = attendanceService.checkOut(employeeId, source);

        log.info("Successfully recorded check-out for employee: {}", employeeId);
        return ResponseEntity.ok(attendance);
    }

    /**
     * Mark employee as absent
     */
    @PostMapping("/absent")
    public ResponseEntity<AttendanceResponseDto> markAsAbsent(
            @RequestParam UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String reason) {
        log.info("POST /api/v1/attendances/absent - Marking employee: {} as absent on date: {}",
                employeeId, date);

        AttendanceResponseDto absence = attendanceService.markAsAbsent(employeeId, date, reason);

        log.info("Successfully marked employee: {} as absent on date: {}", employeeId, date);
        return ResponseEntity.status(HttpStatus.CREATED).body(absence);
    }

    // READ operations

    /**
     * Get attendance by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> getAttendanceById(@PathVariable UUID id) {
        log.info("GET /api/v1/attendances/{} - Fetching attendance by ID", id);

        AttendanceResponseDto attendance = attendanceService.getAttendanceById(id);

        log.info("Successfully found attendance with ID: {}", id);
        return ResponseEntity.ok(attendance);
    }

    /**
     * Get all attendances (paginated)
     */
    @GetMapping
    public ResponseEntity<Page<AttendanceResponseDto>> getAllAttendances(
            @PageableDefault(size = 20, sort = "checkIn") Pageable pageable) {
        log.info("GET /api/v1/attendances - Fetching all attendances with pagination: {}", pageable);

        Page<AttendanceResponseDto> attendances = attendanceService.getAllAttendances(pageable);

        log.info("Returning {} attendances out of {}",
                attendances.getContent().size(), attendances.getTotalElements());
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get all attendances (non-paginated)
     */
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendances() {
        log.info("GET /api/v1/attendances/all - Fetching all attendances");

        List<AttendanceResponseDto> attendances = attendanceService.getAllAttendances();

        log.info("Returning {} attendances", attendances.size());
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get attendances by employee
     */
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByEmployee(
            @PathVariable UUID employeeId) {
        log.info("GET /api/v1/attendances/employee/{} - Fetching attendances by employee", employeeId);

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByEmployee(employeeId);

        log.info("Returning {} attendances for employee: {}", attendances.size(), employeeId);
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get attendances by schedule
     */
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesBySchedule(
            @PathVariable UUID scheduleId) {
        log.info("GET /api/v1/attendances/schedule/{} - Fetching attendances by schedule", scheduleId);

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesBySchedule(scheduleId);

        log.info("Returning {} attendances for schedule: {}", attendances.size(), scheduleId);
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get attendances by date range
     */
    @GetMapping("/date-range")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("GET /api/v1/attendances/date-range - Fetching attendances from {} to {}", startDate, endDate);

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByDateRange(startDate, endDate);

        log.info("Returning {} attendances between {} and {}", attendances.size(), startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get attendances by employee and date
     */
    @GetMapping("/employee/{employeeId}/date")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByEmployeeAndDate(
            @PathVariable UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("GET /api/v1/attendances/employee/{}/date - Fetching attendances for employee on date: {}",
                employeeId, date);

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByEmployeeAndDate(employeeId, date);

        log.info("Returning {} attendances for employee: {} on date: {}",
                attendances.size(), employeeId, date);
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get attendances by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByStatus(
            @PathVariable String status) {
        log.info("GET /api/v1/attendances/status/{} - Fetching attendances by status", status);

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByStatus(status);

        log.info("Returning {} attendances with status: {}", attendances.size(), status);
        return ResponseEntity.ok(attendances);
    }

    /**
     * Get attendance summary by employee
     */
    @GetMapping("/employee/{employeeId}/summary")
    public ResponseEntity<List<AttendanceSummaryDto>> getAttendanceSummaryByEmployee(
            @PathVariable UUID employeeId) {
        log.info("GET /api/v1/attendances/employee/{}/summary - Fetching attendance summary for employee",
                employeeId);

        List<AttendanceSummaryDto> summary = attendanceService.getAttendanceSummaryByEmployee(employeeId);

        log.info("Returning attendance summary with {} records for employee: {}", summary.size(), employeeId);
        return ResponseEntity.ok(summary);
    }

    /**
     * Get current attendance (checked in but not checked out)
     */
    @GetMapping("/employee/{employeeId}/current")
    public ResponseEntity<AttendanceResponseDto> getCurrentAttendance(@PathVariable UUID employeeId) {
        log.info("GET /api/v1/attendances/employee/{}/current - Fetching current attendance for employee",
                employeeId);

        Optional<AttendanceResponseDto> currentAttendance = attendanceService.getCurrentAttendance(employeeId);

        if (currentAttendance.isPresent()) {
            log.info("Found current attendance for employee: {}", employeeId);
            return ResponseEntity.ok(currentAttendance.get());
        } else {
            log.info("No current attendance found for employee: {}", employeeId);
            return ResponseEntity.noContent().build();
        }
    }

    // UPDATE operations

    /**
     * Update attendance record
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(
            @PathVariable UUID id,
            @Valid @RequestBody AttendanceRequestDto requestDto) {
        log.info("PUT /api/v1/attendances/{} - Updating attendance record", id);

        AttendanceResponseDto updatedAttendance = attendanceService.updateAttendance(id, requestDto);

        log.info("Successfully updated attendance record with ID: {}", id);
        return ResponseEntity.ok(updatedAttendance);
    }

    /**
     * Update attendance status
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<AttendanceResponseDto> updateAttendanceStatus(
            @PathVariable UUID id,
            @RequestParam String status) {
        log.info("PATCH /api/v1/attendances/{}/status - Updating attendance status to: {}", id, status);

        AttendanceResponseDto updatedAttendance = attendanceService.updateAttendanceStatus(id, status);

        log.info("Successfully updated status for attendance record ID: {}", id);
        return ResponseEntity.ok(updatedAttendance);
    }

    /**
     * Mark attendance as late
     */
    @PatchMapping("/{id}/late")
    public ResponseEntity<AttendanceResponseDto> markAsLate(
            @PathVariable UUID id,
            @RequestParam(required = false) String reason) {
        log.info("PATCH /api/v1/attendances/{}/late - Marking attendance as late", id);

        AttendanceResponseDto updatedAttendance = attendanceService.markAsLate(id, reason);

        log.info("Successfully marked attendance ID: {} as LATE", id);
        return ResponseEntity.ok(updatedAttendance);
    }

    // DELETE operations

    /**
     * Delete attendance record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable UUID id) {
        log.info("DELETE /api/v1/attendances/{} - Deleting attendance record", id);

        attendanceService.deleteAttendance(id);

        log.info("Successfully deleted attendance record with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Delete all attendances for an employee
     */
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<Void> deleteAttendancesByEmployee(@PathVariable UUID employeeId) {
        log.info("DELETE /api/v1/attendances/employee/{} - Deleting all attendances for employee", employeeId);

        attendanceService.deleteAttendancesByEmployee(employeeId);

        log.info("Successfully deleted all attendances for employee: {}", employeeId);
        return ResponseEntity.noContent().build();
    }

    // UTILITY endpoints

    /**
     * Check if attendance exists by ID
     */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> existsById(@PathVariable UUID id) {
        log.debug("GET /api/v1/attendances/{}/exists - Checking attendance existence", id);

        boolean exists = attendanceService.existsById(id);

        return ResponseEntity.ok(exists);
    }

    /**
     * Get total attendance count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getAttendanceCount() {
        log.debug("GET /api/v1/attendances/count - Getting total attendance count");

        long count = attendanceService.getAttendanceCount();

        return ResponseEntity.ok(count);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        log.debug("GET /api/v1/attendances/health - Health check");
        return ResponseEntity.ok("Attendance Service is healthy");
    }
}
