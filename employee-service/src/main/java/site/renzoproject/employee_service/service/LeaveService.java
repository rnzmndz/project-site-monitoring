package site.renzoproject.employee_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.employee_service.dto.LeavePage;
import site.renzoproject.employee_service.dto.LeaveRequestDto;
import site.renzoproject.employee_service.dto.LeaveResponseDto;
import site.renzoproject.employee_service.mapper.EmployeeMapper;
import site.renzoproject.employee_service.mapper.LeaveMapper;
import site.renzoproject.employee_service.model.Leave;
import site.renzoproject.employee_service.repository.LeaveRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final EmployeeMapper employeeMapper;

    /**
     * Create a new leave request
     */
    @Transactional
    public LeaveResponseDto createLeave(LeaveRequestDto requestDto) {
        log.info("Creating new leave request for employeeId: {}", requestDto.getEmployeeId());

        // Validate required fields
        validateLeaveRequest(requestDto);

        // Validate date range
        if (requestDto.getEndDate().isBefore(requestDto.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Leave leave = leaveMapper.toEntity(requestDto);
        leave.setId(UUID.randomUUID()); // Generate unique ID

        // Set default status if not provided
        if (requestDto.getStatus() == null || requestDto.getStatus().trim().isEmpty()) {
            leave.setStatus("REQUESTED");
        }

        Leave savedLeave = leaveRepository.save(leave);
        log.debug("Successfully created leave request with id: {}", savedLeave.getId());

        return leaveMapper.toResponseDto(savedLeave, employeeMapper);
    }

    /**
     * Get leave by ID
     */
    public LeaveResponseDto getLeaveById(UUID id) {
        log.debug("Fetching leave request with id: {}", id);

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Leave request not found with id: {}", id);
                    return new RuntimeException("Leave request not found with id: " + id);
                });

        return leaveMapper.toResponseDto(leave, employeeMapper);
    }

    /**
     * Get all leave requests
     */
    public List<LeaveResponseDto> getAllLeaves() {
        log.debug("Fetching all leave requests");

        List<Leave> leaves = leaveRepository.findAll();
        log.debug("Found {} leave requests", leaves.size());

        return leaveMapper.toResponseDtoList(leaves, employeeMapper);
    }

    /**
     * Get all leave requests with pagination
     */
    public LeavePage getAllLeaves(Pageable pageable) {
        log.debug("Fetching all leave requests with pagination - page: {}, size: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Leave> leavePage = leaveRepository.findAll(pageable);
        List<LeaveResponseDto> content = leaveMapper.toResponseDtoList(leavePage.getContent(), employeeMapper);

        log.debug("Found {} leave requests on page {}", content.size(), pageable.getPageNumber());

        return new LeavePage(content, pageable, leavePage.getTotalElements());
    }

    /**
     * Update an existing leave request
     */
    @Transactional
    public LeaveResponseDto updateLeave(UUID id, LeaveRequestDto requestDto) {
        log.info("Updating leave request with id: {}", id);

        Leave existingLeave = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Leave request not found for update with id: {}", id);
                    return new RuntimeException("Leave request not found with id: " + id);
                });

        // Validate date range if dates are being updated
        if (requestDto.getStartDate() != null && requestDto.getEndDate() != null) {
            if (requestDto.getEndDate().isBefore(requestDto.getStartDate())) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
        }

        // Update the existing entity with new data
        leaveMapper.updateEntityFromDto(requestDto, existingLeave);

        Leave updatedLeave = leaveRepository.save(existingLeave);
        log.debug("Successfully updated leave request with id: {}", id);

        return leaveMapper.toResponseDto(updatedLeave, employeeMapper);
    }

    /**
     * Delete a leave request by ID
     */
    @Transactional
    public void deleteLeave(UUID id) {
        log.info("Deleting leave request with id: {}", id);

        if (!leaveRepository.existsById(id)) {
            log.warn("Leave request not found for deletion with id: {}", id);
            throw new RuntimeException("Leave request not found with id: " + id);
        }

        leaveRepository.deleteById(id);
        log.debug("Successfully deleted leave request with id: {}", id);
    }

    /**
     * Update leave status
     */
    @Transactional
    public LeaveResponseDto updateLeaveStatus(UUID id, String status) {
        log.info("Updating leave status to {} for leave id: {}", status, id);

        Leave leave = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Leave request not found for status update with id: {}", id);
                    return new RuntimeException("Leave request not found with id: " + id);
                });

        // Validate status
        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid leave status: " + status);
        }

        leave.setStatus(status);
        Leave updatedLeave = leaveRepository.save(leave);
        log.debug("Successfully updated leave status to {} for id: {}", status, id);

        return leaveMapper.toResponseDto(updatedLeave, employeeMapper);
    }

    /**
     * Get leaves by employee ID
     */
    public List<LeaveResponseDto> getLeavesByEmployeeId(UUID employeeId) {
        log.debug("Fetching leave requests for employeeId: {}", employeeId);

        List<Leave> employeeLeaves = leaveRepository.findByEmployeeId(employeeId);
        log.debug("Found {} leave requests for employeeId: {}", employeeLeaves.size(), employeeId);

        return leaveMapper.toResponseDtoList(employeeLeaves, employeeMapper);
    }

    /**
     * Get leaves by employee ID with pagination
     */
    public LeavePage getLeavesByEmployeeId(UUID employeeId, Pageable pageable) {
        log.debug("Fetching leave requests for employeeId: {} with pagination", employeeId);

        Page<Leave> leavePage = leaveRepository.findByEmployeeId(employeeId, pageable);
        List<LeaveResponseDto> content = leaveMapper.toResponseDtoList(leavePage.getContent(), employeeMapper);

        log.debug("Found {} leave requests for employeeId: {} on page {}",
                content.size(), employeeId, pageable.getPageNumber());

        return new LeavePage(content, pageable, leavePage.getTotalElements());
    }

    /**
     * Get leaves by status
     */
    public List<LeaveResponseDto> getLeavesByStatus(String status) {
        log.debug("Fetching leave requests with status: {}", status);

        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid leave status: " + status);
        }

        List<Leave> statusLeaves = leaveRepository.findByStatus(status);
        log.debug("Found {} leave requests with status: {}", statusLeaves.size(), status);

        return leaveMapper.toResponseDtoList(statusLeaves, employeeMapper);
    }

    /**
     * Get leaves by status with pagination
     */
    public LeavePage getLeavesByStatus(String status, Pageable pageable) {
        log.debug("Fetching leave requests with status: {} with pagination", status);

        if (!isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid leave status: " + status);
        }

        Page<Leave> leavePage = leaveRepository.findByStatus(status, pageable);
        List<LeaveResponseDto> content = leaveMapper.toResponseDtoList(leavePage.getContent(), employeeMapper);

        log.debug("Found {} leave requests with status: {} on page {}",
                content.size(), status, pageable.getPageNumber());

        return new LeavePage(content, pageable, leavePage.getTotalElements());
    }

    /**
     * Get leaves by date range
     */
    public List<LeaveResponseDto> getLeavesByDateRange(LocalDate startDate, LocalDate endDate) {
        log.debug("Fetching leave requests between {} and {}", startDate, endDate);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        List<Leave> rangeLeaves = leaveRepository.findByStartDateBetweenOrEndDateBetween(
                startDate, endDate, startDate, endDate);
        log.debug("Found {} leave requests in date range", rangeLeaves.size());

        return leaveMapper.toResponseDtoList(rangeLeaves, employeeMapper);
    }

    /**
     * Get overlapping leaves for an employee
     */
    public List<LeaveResponseDto> getOverlappingLeaves(UUID employeeId, LocalDate startDate, LocalDate endDate) {
        log.debug("Checking for overlapping leaves for employeeId: {} between {} and {}",
                employeeId, startDate, endDate);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        List<Leave> overlappingLeaves = leaveRepository.findOverlappingLeaves(employeeId, startDate, endDate);
        log.debug("Found {} overlapping leave requests for employeeId: {}",
                overlappingLeaves.size(), employeeId);

        return leaveMapper.toResponseDtoList(overlappingLeaves, employeeMapper);
    }

    /**
     * Check if leave request exists by ID
     */
    public boolean leaveExists(UUID id) {
        return leaveRepository.existsById(id);
    }

    /**
     * Check if employee has overlapping leave requests
     */
    public boolean hasOverlappingLeave(UUID employeeId, LocalDate startDate, LocalDate endDate, UUID excludeLeaveId) {
        log.debug("Checking for overlapping leaves for employeeId: {} between {} and {}, excluding leaveId: {}",
                employeeId, startDate, endDate, excludeLeaveId);

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        List<Leave> overlappingLeaves = leaveRepository.findOverlappingLeavesExcludingId(
                employeeId, startDate, endDate, excludeLeaveId);

        boolean hasOverlap = !overlappingLeaves.isEmpty();
        log.debug("Employee {} has overlapping leaves: {}", employeeId, hasOverlap);

        return hasOverlap;
    }

    /**
     * Validate leave request
     */
    private void validateLeaveRequest(LeaveRequestDto requestDto) {
        if (requestDto.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        if (requestDto.getLeaveType() == null || requestDto.getLeaveType().trim().isEmpty()) {
            throw new IllegalArgumentException("Leave type cannot be null or empty");
        }
        if (requestDto.getStartDate() == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (requestDto.getEndDate() == null) {
            throw new IllegalArgumentException("End date cannot be null");
        }
    }

    /**
     * Validate leave status
     */
    private boolean isValidStatus(String status) {
        return status != null &&
                (status.equals("REQUESTED") ||
                        status.equals("APPROVED") ||
                        status.equals("REJECTED") ||
                        status.equals("CANCELLED"));
    }
}
