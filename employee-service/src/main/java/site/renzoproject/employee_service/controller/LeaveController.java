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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.renzoproject.employee_service.dto.LeavePage;
import site.renzoproject.employee_service.dto.LeaveRequestDto;
import site.renzoproject.employee_service.dto.LeaveResponseDto;
import site.renzoproject.employee_service.service.LeaveService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/leaves")
@Tag(name = "Leave Management", description = "APIs for managing employee leave requests")
public class LeaveController {

    private final LeaveService leaveService;

    @Operation(summary = "Create a new leave request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Leave request created successfully",
                    content = @Content(schema = @Schema(implementation = LeaveResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Overlapping leave request exists")
    })
    @PostMapping
    public ResponseEntity<LeaveResponseDto> createLeave(
            @Valid @RequestBody LeaveRequestDto leaveRequestDto) {
        log.info("POST /api/v1/leaves - Creating new leave request for employee: {}",
                leaveRequestDto.getEmployeeId());

        LeaveResponseDto response = leaveService.createLeave(leaveRequestDto);
        log.debug("Successfully created leave request with id: {}", response.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get leave request by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave request found",
                    content = @Content(schema = @Schema(implementation = LeaveResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Leave request not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponseDto> getLeaveById(
            @Parameter(description = "ID of the leave request to retrieve")
            @PathVariable UUID id) {
        log.debug("GET /api/v1/leaves/{} - Fetching leave request", id);

        LeaveResponseDto response = leaveService.getLeaveById(id);
        log.debug("Successfully fetched leave request with id: {}", id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all leave requests")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all leave requests")
    @GetMapping
    public ResponseEntity<List<LeaveResponseDto>> getAllLeaves() {
        log.debug("GET /api/v1/leaves - Fetching all leave requests");

        List<LeaveResponseDto> responses = leaveService.getAllLeaves();
        log.debug("Successfully fetched {} leave requests", responses.size());

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get all leave requests with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated leave requests")
    @GetMapping("/paginated")
    public ResponseEntity<LeavePage> getAllLeavesPaginated(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort by field (createdAt, startDate, endDate)")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (ASC, DESC)")
            @RequestParam(defaultValue = "DESC") String direction) {
        log.debug("GET /api/v1/leaves/paginated - Fetching leave requests page: {}, size: {}", page, size);

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        LeavePage response = leaveService.getAllLeaves(pageable);
        log.debug("Successfully fetched {} leave requests on page {}",
                response.getContent().size(), page);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing leave request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave request updated successfully",
                    content = @Content(schema = @Schema(implementation = LeaveResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Leave request not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LeaveResponseDto> updateLeave(
            @Parameter(description = "ID of the leave request to update")
            @PathVariable UUID id,
            @Valid @RequestBody LeaveRequestDto leaveRequestDto) {
        log.info("PUT /api/v1/leaves/{} - Updating leave request", id);

        LeaveResponseDto response = leaveService.updateLeave(id, leaveRequestDto);
        log.debug("Successfully updated leave request with id: {}", id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update leave request status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Leave status updated successfully",
                    content = @Content(schema = @Schema(implementation = LeaveResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Leave request not found"),
            @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<LeaveResponseDto> updateLeaveStatus(
            @Parameter(description = "ID of the leave request to update")
            @PathVariable UUID id,
            @Parameter(description = "New status (REQUESTED, APPROVED, REJECTED, CANCELLED)")
            @RequestParam String status) {
        log.info("PATCH /api/v1/leaves/{}/status - Updating leave status to: {}", id, status);

        LeaveResponseDto response = leaveService.updateLeaveStatus(id, status);
        log.debug("Successfully updated leave status to {} for id: {}", status, id);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a leave request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Leave request deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Leave request not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(
            @Parameter(description = "ID of the leave request to delete")
            @PathVariable UUID id) {
        log.info("DELETE /api/v1/leaves/{} - Deleting leave request", id);

        leaveService.deleteLeave(id);
        log.debug("Successfully deleted leave request with id: {}", id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get leave requests by employee ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved leave requests for employee")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveResponseDto>> getLeavesByEmployeeId(
            @Parameter(description = "ID of the employee")
            @PathVariable UUID employeeId) {
        log.debug("GET /api/v1/leaves/employee/{} - Fetching leave requests for employee", employeeId);

        List<LeaveResponseDto> responses = leaveService.getLeavesByEmployeeId(employeeId);
        log.debug("Successfully fetched {} leave requests for employeeId: {}",
                responses.size(), employeeId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get leave requests by employee ID with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated leave requests for employee")
    @GetMapping("/employee/{employeeId}/paginated")
    public ResponseEntity<LeavePage> getLeavesByEmployeeIdPaginated(
            @Parameter(description = "ID of the employee")
            @PathVariable UUID employeeId,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort by field (createdAt, startDate, endDate)")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (ASC, DESC)")
            @RequestParam(defaultValue = "DESC") String direction) {
        log.debug("GET /api/v1/leaves/employee/{}/paginated - Fetching leave requests for employee", employeeId);

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        LeavePage response = leaveService.getLeavesByEmployeeId(employeeId, pageable);
        log.debug("Successfully fetched {} leave requests for employeeId: {} on page {}",
                response.getContent().size(), employeeId, page);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get leave requests by status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved leave requests by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveResponseDto>> getLeavesByStatus(
            @Parameter(description = "Status to filter by (REQUESTED, APPROVED, REJECTED, CANCELLED)")
            @PathVariable String status) {
        log.debug("GET /api/v1/leaves/status/{} - Fetching leave requests by status", status);

        List<LeaveResponseDto> responses = leaveService.getLeavesByStatus(status);
        log.debug("Successfully fetched {} leave requests with status: {}", responses.size(), status);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get leave requests by status with pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated leave requests by status")
    @GetMapping("/status/{status}/paginated")
    public ResponseEntity<LeavePage> getLeavesByStatusPaginated(
            @Parameter(description = "Status to filter by (REQUESTED, APPROVED, REJECTED, CANCELLED)")
            @PathVariable String status,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort by field (createdAt, startDate, endDate)")
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @Parameter(description = "Sort direction (ASC, DESC)")
            @RequestParam(defaultValue = "DESC") String direction) {
        log.debug("GET /api/v1/leaves/status/{}/paginated - Fetching leave requests by status", status);

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        LeavePage response = leaveService.getLeavesByStatus(status, pageable);
        log.debug("Successfully fetched {} leave requests with status: {} on page {}",
                response.getContent().size(), status, page);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get leave requests by date range")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved leave requests in date range")
    @GetMapping("/date-range")
    public ResponseEntity<List<LeaveResponseDto>> getLeavesByDateRange(
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/v1/leaves/date-range - Fetching leave requests between {} and {}", startDate, endDate);

        List<LeaveResponseDto> responses = leaveService.getLeavesByDateRange(startDate, endDate);
        log.debug("Successfully fetched {} leave requests in date range", responses.size());

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get overlapping leave requests for an employee")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved overlapping leave requests")
    @GetMapping("/employee/{employeeId}/overlapping")
    public ResponseEntity<List<LeaveResponseDto>> getOverlappingLeaves(
            @Parameter(description = "ID of the employee")
            @PathVariable UUID employeeId,
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.debug("GET /api/v1/leaves/employee/{}/overlapping - Checking overlapping leaves between {} and {}",
                employeeId, startDate, endDate);

        List<LeaveResponseDto> responses = leaveService.getOverlappingLeaves(employeeId, startDate, endDate);
        log.debug("Found {} overlapping leave requests for employeeId: {}", responses.size(), employeeId);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Check if leave request exists")
    @ApiResponse(responseCode = "200", description = "Successfully checked leave request existence")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkLeaveExists(
            @Parameter(description = "ID of the leave request to check")
            @PathVariable UUID id) {
        log.debug("GET /api/v1/leaves/{}/exists - Checking leave request existence", id);

        boolean exists = leaveService.leaveExists(id);
        log.debug("Leave request with id {} exists: {}", id, exists);

        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Check if employee has overlapping leave requests")
    @ApiResponse(responseCode = "200", description = "Successfully checked for overlapping leaves")
    @GetMapping("/employee/{employeeId}/has-overlap")
    public ResponseEntity<Boolean> checkOverlappingLeave(
            @Parameter(description = "ID of the employee")
            @PathVariable UUID employeeId,
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Leave ID to exclude from check (optional)")
            @RequestParam(required = false) UUID excludeLeaveId) {
        log.debug("GET /api/v1/leaves/employee/{}/has-overlap - Checking overlapping leaves between {} and {}",
                employeeId, startDate, endDate);

        boolean hasOverlap = leaveService.hasOverlappingLeave(employeeId, startDate, endDate, excludeLeaveId);
        log.debug("Employee {} has overlapping leaves: {}", employeeId, hasOverlap);

        return ResponseEntity.ok(hasOverlap);
    }
}
