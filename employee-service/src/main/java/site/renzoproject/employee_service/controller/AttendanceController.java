package site.renzoproject.employee_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import site.renzoproject.employee_service.dto.AttendancePage;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.dto.AttendanceSummaryDto;
import site.renzoproject.employee_service.service.AttendanceService;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/attendances")
@Tag(name = "Attendance Management", description = "Endpoints for managing employee attendance records")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // --------------------------------------------------
    // CREATE OPERATIONS
    // --------------------------------------------------

    @Operation(summary = "Create attendance", description = "Create a new attendance record for an employee.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Attendance created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<AttendanceResponseDto> createAttendance(
            @Valid @RequestBody AttendanceRequestDto requestDto) {

        log.info("Creating new attendance for employee: {}", requestDto.getEmployeeId());
        AttendanceResponseDto created = attendanceService.createAttendance(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Bulk create attendances", description = "Create multiple attendance records at once.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Attendances created successfully")
    })
    @PostMapping("/bulk")
    public ResponseEntity<Map<String, Object>> bulkCreateAttendances(
            @Valid @RequestBody List<AttendanceRequestDto> requestDtos) {

        log.info("Bulk creating {} attendance records", requestDtos.size());
        attendanceService.bulkCreateAttendances(requestDtos);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("createdCount", requestDtos.size()));
    }

    @Operation(summary = "Check-in employee", description = "Record the start of an employee's work attendance.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Check-in recorded successfully")
    })
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponseDto> checkIn(
            @Parameter(description = "Employee ID") @RequestParam UUID employeeId,
            @Parameter(description = "Associated schedule ID (optional)") @RequestParam(required = false) UUID scheduleId,
            @Parameter(description = "Source (e.g., mobile, kiosk)") @RequestParam(required = false) String source) {

        AttendanceResponseDto attendance = attendanceService.checkIn(employeeId, scheduleId, source);
        return ResponseEntity.status(HttpStatus.CREATED).body(attendance);
    }

    @Operation(summary = "Check-out employee", description = "Record the end of an employee's work attendance.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-out recorded successfully")
    })
    @PostMapping("/check-out")
    public ResponseEntity<AttendanceResponseDto> checkOut(
            @Parameter(description = "Employee ID") @RequestParam UUID employeeId,
            @Parameter(description = "Source (e.g., mobile, kiosk)") @RequestParam(required = false) String source) {

        AttendanceResponseDto attendance = attendanceService.checkOut(employeeId, source);
        return ResponseEntity.ok(attendance);
    }

    @Operation(summary = "Mark as absent", description = "Mark an employee as absent for a specific date.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Marked as absent successfully")
    })
    @PostMapping("/absent")
    public ResponseEntity<AttendanceResponseDto> markAsAbsent(
            @Parameter(description = "Employee ID") @RequestParam UUID employeeId,
            @Parameter(description = "Date of absence") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "Reason for absence (optional)") @RequestParam(required = false) String reason) {

        AttendanceResponseDto absence = attendanceService.markAsAbsent(employeeId, date, reason);
        return ResponseEntity.status(HttpStatus.CREATED).body(absence);
    }

    // --------------------------------------------------
    // READ OPERATIONS
    // --------------------------------------------------

    @Operation(summary = "Get attendance by ID", description = "Retrieve a specific attendance record by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> getAttendanceById(@PathVariable UUID id) {
        AttendanceResponseDto attendance = attendanceService.getAttendanceById(id);
        return ResponseEntity.ok(attendance);
    }

    @Operation(summary = "Get all attendances (paginated)", description = "Retrieve all attendance records with pagination support.")
    @GetMapping
    public ResponseEntity<AttendancePage> getAllAttendances(
            @PageableDefault(size = 20, sort = "checkIn") Pageable pageable) {

        Page<AttendanceResponseDto> page = attendanceService.getAllAttendances(pageable);
        AttendancePage customPage = new AttendancePage(page.getContent(), pageable, page.getTotalElements());
        return ResponseEntity.ok(customPage);
    }

    @Operation(summary = "Get all attendances", description = "Retrieve all attendance records without pagination.")
    @GetMapping("/all")
    public ResponseEntity<List<AttendanceResponseDto>> getAllAttendances() {
        List<AttendanceResponseDto> attendances = attendanceService.getAllAttendances();
        return ResponseEntity.ok(attendances);
    }

    @Operation(summary = "Get employee attendances", description = "Retrieve all attendance records for a specific employee.")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByEmployee(@PathVariable UUID employeeId) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByEmployee(employeeId);
        return ResponseEntity.ok(attendances);
    }

    @Operation(summary = "Get attendances by schedule", description = "Retrieve all attendance records for a specific schedule.")
    @GetMapping("/schedule/{scheduleId}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesBySchedule(@PathVariable UUID scheduleId) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesBySchedule(scheduleId);
        return ResponseEntity.ok(attendances);
    }

    @Operation(summary = "Get attendances by date range", description = "Retrieve attendances between two dates.")
    @GetMapping("/date-range")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (endDate.isBefore(startDate)) {
            return ResponseEntity.badRequest()
                    .body(Collections.emptyList());
        }

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByDateRange(startDate, endDate);
        return ResponseEntity.ok(attendances);
    }

    @Operation(summary = "Get attendances by employee and date", description = "Retrieve attendances for an employee on a specific date.")
    @GetMapping("/employee/{employeeId}/date")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByEmployeeAndDate(
            @PathVariable UUID employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByEmployeeAndDate(employeeId, date);
        return ResponseEntity.ok(attendances);
    }

    @Operation(summary = "Get attendances by status", description = "Retrieve all attendances with a given status (e.g., PRESENT, ABSENT, LATE).")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<AttendanceResponseDto>> getAttendancesByStatus(@PathVariable String status) {
        List<AttendanceResponseDto> attendances = attendanceService.getAttendancesByStatus(status);
        return ResponseEntity.ok(attendances);
    }

    @Operation(summary = "Get attendance summary", description = "Retrieve summarized attendance data for an employee.")
    @GetMapping("/employee/{employeeId}/summary")
    public ResponseEntity<List<AttendanceSummaryDto>> getAttendanceSummaryByEmployee(@PathVariable UUID employeeId) {
        List<AttendanceSummaryDto> summary = attendanceService.getAttendanceSummaryByEmployee(employeeId);
        return ResponseEntity.ok(summary);
    }

    @Operation(summary = "Get current attendance", description = "Retrieve current ongoing attendance (checked-in but not yet checked-out).")
    @GetMapping("/employee/{employeeId}/current")
    public ResponseEntity<AttendanceResponseDto> getCurrentAttendance(@PathVariable UUID employeeId) {
        return attendanceService.getCurrentAttendance(employeeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    // --------------------------------------------------
    // UPDATE OPERATIONS
    // --------------------------------------------------

    @Operation(summary = "Update attendance record", description = "Update an existing attendance record.")
    @PutMapping("/{id}")
    public ResponseEntity<AttendanceResponseDto> updateAttendance(
            @PathVariable UUID id,
            @Valid @RequestBody AttendanceRequestDto requestDto) {

        AttendanceResponseDto updated = attendanceService.updateAttendance(id, requestDto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Update attendance status", description = "Update the status (e.g., PRESENT, ABSENT, LATE) of a specific attendance.")
    @PatchMapping("/{id}/status")
    public ResponseEntity<AttendanceResponseDto> updateAttendanceStatus(
            @PathVariable UUID id,
            @RequestParam String status) {

        AttendanceResponseDto updated = attendanceService.updateAttendanceStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Mark attendance as late", description = "Mark a specific attendance record as late with an optional reason.")
    @PatchMapping("/{id}/late")
    public ResponseEntity<AttendanceResponseDto> markAsLate(
            @PathVariable UUID id,
            @RequestParam(required = false) String reason) {

        AttendanceResponseDto updated = attendanceService.markAsLate(id, reason);
        return ResponseEntity.ok(updated);
    }

    // --------------------------------------------------
    // DELETE OPERATIONS
    // --------------------------------------------------

    @Operation(summary = "Delete attendance", description = "Delete a specific attendance record by ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable UUID id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete employee attendances", description = "Delete all attendance records for a specific employee.")
    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<Void> deleteAttendancesByEmployee(@PathVariable UUID employeeId) {
        attendanceService.deleteAttendancesByEmployee(employeeId);
        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    // UTILITY ENDPOINTS
    // --------------------------------------------------

    @Operation(summary = "Check attendance existence", description = "Check whether an attendance record exists by ID.")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> existsById(@PathVariable UUID id) {
        boolean exists = attendanceService.existsById(id);
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @Operation(summary = "Get total attendance count", description = "Retrieve the total number of attendance records.")
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getAttendanceCount() {
        long count = attendanceService.getAttendanceCount();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @Operation(summary = "Health check", description = "Verify that the Attendance service is up and running.")
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of("status", "Attendance Service is healthy"));
    }
}