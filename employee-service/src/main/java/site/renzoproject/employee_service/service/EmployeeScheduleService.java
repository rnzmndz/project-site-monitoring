package site.renzoproject.employee_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.employee_service.dto.EmployeeSchedulePage;
import site.renzoproject.employee_service.dto.EmployeeScheduleRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleResponseDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleSummaryDto;
import site.renzoproject.employee_service.exception.EmployeeScheduleNotFoundException;
import site.renzoproject.employee_service.exception.InvalidScheduleException;
import site.renzoproject.employee_service.mapper.EmployeeScheduleMapper;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.repository.EmployeeScheduleRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeScheduleService {

    private final EmployeeScheduleRepository employeeScheduleRepository;
    private final EmployeeScheduleMapper employeeScheduleMapper;

    // CREATE operations

    /**
     * Create a new employee schedule
     */
    public EmployeeScheduleResponseDto createEmployeeSchedule(EmployeeScheduleRequestDto requestDto) {
        log.info("Creating new employee schedule: {}", requestDto.getDescription());

        validateScheduleRequest(requestDto);

        EmployeeSchedule employeeSchedule = employeeScheduleMapper.toEntity(requestDto);
        employeeSchedule.setId(UUID.randomUUID());

        // Set default values if not provided
        if (employeeSchedule.getStatus() == null) {
            employeeSchedule.setStatus("PLANNED");
        }

        if (employeeSchedule.getScheduleType() == null) {
            employeeSchedule.setScheduleType("SHIFT");
        }

        EmployeeSchedule savedSchedule = employeeScheduleRepository.save(employeeSchedule);
        log.info("Successfully created employee schedule with ID: {}", savedSchedule.getId());

        return employeeScheduleMapper.toResponseDto(savedSchedule);
    }

    /**
     * Bulk create employee schedules
     */
    public void bulkCreateEmployeeSchedules(List<EmployeeScheduleRequestDto> requestDtos) {
        log.info("Bulk creating {} employee schedules", requestDtos.size());

        List<EmployeeSchedule> schedules = requestDtos.stream()
                .map(dto -> {
                    validateScheduleRequest(dto);
                    EmployeeSchedule schedule = employeeScheduleMapper.toEntity(dto);
                    schedule.setId(UUID.randomUUID());

                    // Set default values if not provided
                    if (schedule.getStatus() == null) {
                        schedule.setStatus("PLANNED");
                    }

                    if (schedule.getScheduleType() == null) {
                        schedule.setScheduleType("SHIFT");
                    }

                    return schedule;
                })
                .toList();

        List<EmployeeSchedule> savedSchedules = employeeScheduleRepository.saveAll(schedules);
        log.info("Successfully created {} employee schedules in bulk", savedSchedules.size());
    }

    // READ operations

    /**
     * Get employee schedule by ID
     */
    @Transactional(readOnly = true)
    public EmployeeScheduleResponseDto getEmployeeScheduleById(UUID id) {
        log.debug("Fetching employee schedule with ID: {}", id);

        EmployeeSchedule employeeSchedule = employeeScheduleRepository.findById(id)
                .orElseThrow(() -> new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id));

        return employeeScheduleMapper.toResponseDto(employeeSchedule);
    }

    /**
     * Get all employee schedules with pagination
     */
    @Transactional(readOnly = true)
    public EmployeeSchedulePage getAllEmployeeSchedules(Pageable pageable) {
        log.debug("Fetching all employee schedules with pagination: {}", pageable);

        Page<EmployeeSchedule> schedulePage = employeeScheduleRepository.findAll(pageable);
        List<EmployeeScheduleResponseDto> content = schedulePage.getContent()
                .stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();

        log.debug("Found {} employee schedules", schedulePage.getTotalElements());
        return new EmployeeSchedulePage(content, pageable, schedulePage.getTotalElements());
    }

    /**
     * Get all employee schedules (non-paginated)
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getAllEmployeeSchedules() {
        log.debug("Fetching all employee schedules");

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findAll();
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    /**
     * Get employee schedules by type
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getEmployeeSchedulesByType(String scheduleType) {
        log.debug("Fetching employee schedules by type: {}", scheduleType);

        validateScheduleType(scheduleType);

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByScheduleType(scheduleType);
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    /**
     * Get employee schedules by status
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getEmployeeSchedulesByStatus(String status) {
        log.debug("Fetching employee schedules by status: {}", status);

        validateScheduleStatus(status);

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByStatus(status);
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    /**
     * Get employee schedules by date range
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getEmployeeSchedulesByDateRange(LocalDate startDate, LocalDate endDate) {
        log.debug("Fetching employee schedules between {} and {}", startDate, endDate);

        Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByStartTimeBetween(startInstant, endInstant);
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    /**
     * Get employee schedules that overlap with time range
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getEmployeeSchedulesOverlapping(Instant startTime, Instant endTime) {
        log.debug("Fetching employee schedules overlapping with {} to {}", startTime, endTime);

        validateTimeRange(startTime, endTime);

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findOverlappingSchedules(startTime, endTime);
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    /**
     * Get active employee schedules (not cancelled)
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getActiveEmployeeSchedules() {
        log.debug("Fetching active employee schedules");

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByStatusNot("CANCELLED");
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    /**
     * Get employee schedules summary
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleSummaryDto> getEmployeeSchedulesSummary() {
        log.debug("Fetching employee schedules summary");

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findAll();
        return schedules.stream()
                .map(employeeScheduleMapper::toSummaryDto)
                .toList();
    }

    /**
     * Get employee schedules summary by type
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleSummaryDto> getEmployeeSchedulesSummaryByType(String scheduleType) {
        log.debug("Fetching employee schedules summary by type: {}", scheduleType);

        validateScheduleType(scheduleType);

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByScheduleType(scheduleType);
        return schedules.stream()
                .map(employeeScheduleMapper::toSummaryDto)
                .toList();
    }

    /**
     * Get upcoming employee schedules
     */
    @Transactional(readOnly = true)
    public List<EmployeeScheduleResponseDto> getUpcomingEmployeeSchedules(int days) {
        log.debug("Fetching upcoming employee schedules for next {} days", days);

        Instant now = Instant.now();
        Instant future = now.plusSeconds(days * 24 * 60 * 60L);

        List<EmployeeSchedule> schedules = employeeScheduleRepository.findByStartTimeBetweenAndStatusNot(
                now, future, "CANCELLED");
        return schedules.stream()
                .map(employeeScheduleMapper::toResponseDto)
                .toList();
    }

    // UPDATE operations

    /**
     * Update employee schedule
     */
    public EmployeeScheduleResponseDto updateEmployeeSchedule(UUID id, EmployeeScheduleRequestDto requestDto) {
        log.info("Updating employee schedule with ID: {}", id);

        EmployeeSchedule existingSchedule = employeeScheduleRepository.findById(id)
                .orElseThrow(() -> new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id));

        validateScheduleRequest(requestDto);

        // Store original values for logging
        Instant originalStartTime = existingSchedule.getStartTime();
        Instant originalEndTime = existingSchedule.getEndTime();
        String originalStatus = existingSchedule.getStatus();

        employeeScheduleMapper.updateEntityFromDto(requestDto, existingSchedule);

        EmployeeSchedule updatedSchedule = employeeScheduleRepository.save(existingSchedule);

        log.info("Successfully updated employee schedule ID: {}. StartTime: {}->{}, EndTime: {}->{}, Status: {}->{}",
                id, originalStartTime, updatedSchedule.getStartTime(),
                originalEndTime, updatedSchedule.getEndTime(),
                originalStatus, updatedSchedule.getStatus());

        return employeeScheduleMapper.toResponseDto(updatedSchedule);
    }

    /**
     * Update employee schedule status
     */
    public EmployeeScheduleResponseDto updateEmployeeScheduleStatus(UUID id, String status) {
        log.info("Updating status for employee schedule ID: {} to {}", id, status);

        EmployeeSchedule schedule = employeeScheduleRepository.findById(id)
                .orElseThrow(() -> new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id));

        validateScheduleStatus(status);

        schedule.setStatus(status);
        EmployeeSchedule updatedSchedule = employeeScheduleRepository.save(schedule);

        log.info("Successfully updated status for employee schedule ID: {}", id);
        return employeeScheduleMapper.toResponseDto(updatedSchedule);
    }

    /**
     * Approve employee schedule
     */
    public EmployeeScheduleResponseDto approveEmployeeSchedule(UUID id) {
        log.info("Approving employee schedule with ID: {}", id);

        EmployeeSchedule schedule = employeeScheduleRepository.findById(id)
                .orElseThrow(() -> new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id));

        if ("CANCELLED".equals(schedule.getStatus())) {
            throw new InvalidScheduleException("Cannot approve a cancelled schedule");
        }

        schedule.setStatus("APPROVED");
        EmployeeSchedule updatedSchedule = employeeScheduleRepository.save(schedule);

        log.info("Successfully approved employee schedule with ID: {}", id);
        return employeeScheduleMapper.toResponseDto(updatedSchedule);
    }

    /**
     * Cancel employee schedule
     */
    public EmployeeScheduleResponseDto cancelEmployeeSchedule(UUID id, String reason) {
        log.info("Cancelling employee schedule with ID: {}", id);

        EmployeeSchedule schedule = employeeScheduleRepository.findById(id)
                .orElseThrow(() -> new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id));

        schedule.setStatus("CANCELLED");
        if (reason != null && schedule.getDescription() != null) {
            schedule.setDescription(schedule.getDescription() + " [CANCELLED: " + reason + "]");
        }

        EmployeeSchedule updatedSchedule = employeeScheduleRepository.save(schedule);

        log.info("Successfully cancelled employee schedule with ID: {}", id);
        return employeeScheduleMapper.toResponseDto(updatedSchedule);
    }

    /**
     * Reschedule employee schedule
     */
    public EmployeeScheduleResponseDto rescheduleEmployeeSchedule(UUID id, Instant newStartTime, Instant newEndTime) {
        log.info("Rescheduling employee schedule with ID: {} to {} - {}", id, newStartTime, newEndTime);

        EmployeeSchedule schedule = employeeScheduleRepository.findById(id)
                .orElseThrow(() -> new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id));

        validateTimeRange(newStartTime, newEndTime);

        // Check for conflicts with other schedules
        if (hasScheduleConflict(schedule, newStartTime, newEndTime)) {
            throw new InvalidScheduleException("Schedule conflict detected for the new time range");
        }

        schedule.setStartTime(newStartTime);
        schedule.setEndTime(newEndTime);
        EmployeeSchedule updatedSchedule = employeeScheduleRepository.save(schedule);

        log.info("Successfully rescheduled employee schedule with ID: {}", id);
        return employeeScheduleMapper.toResponseDto(updatedSchedule);
    }

    // DELETE operations

    /**
     * Delete employee schedule
     */
    public void deleteEmployeeSchedule(UUID id) {
        log.info("Deleting employee schedule with ID: {}", id);

        if (!employeeScheduleRepository.existsById(id)) {
            throw new EmployeeScheduleNotFoundException("Employee schedule not found with ID: " + id);
        }

        employeeScheduleRepository.deleteById(id);
        log.info("Successfully deleted employee schedule with ID: {}", id);
    }

    /**
     * Delete employee schedules by status
     */
    public long deleteEmployeeSchedulesByStatus(String status) {
        log.info("Deleting employee schedules with status: {}", status);

        validateScheduleStatus(status);

        long deletedCount = employeeScheduleRepository.deleteByStatus(status);
        log.info("Successfully deleted {} employee schedules with status: {}", deletedCount, status);
        return deletedCount;
    }

    // BUSINESS LOGIC methods

    /**
     * Validate schedule request
     */
    private void validateScheduleRequest(EmployeeScheduleRequestDto requestDto) {
        if (requestDto == null) {
            throw new InvalidScheduleException("Schedule request cannot be null");
        }

        if (requestDto.getStartTime() == null) {
            throw new InvalidScheduleException("Start time is required");
        }

        if (requestDto.getEndTime() == null) {
            throw new InvalidScheduleException("End time is required");
        }

        validateTimeRange(requestDto.getStartTime(), requestDto.getEndTime());

        if (requestDto.getScheduleType() != null) {
            validateScheduleType(requestDto.getScheduleType());
        }

        if (requestDto.getStatus() != null) {
            validateScheduleStatus(requestDto.getStatus());
        }
    }

    /**
     * Validate time range
     */
    private void validateTimeRange(Instant startTime, Instant endTime) {
        if (startTime.isAfter(endTime)) {
            throw new InvalidScheduleException("Start time cannot be after end time");
        }

        if (startTime.isBefore(Instant.now())) {
            throw new InvalidScheduleException("Cannot create schedule in the past");
        }
    }

    /**
     * Validate schedule type
     */
    private void validateScheduleType(String scheduleType) {
        List<String> validTypes = List.of("SHIFT", "LEAVE", "TRAINING", "MEETING", "OVERTIME");
        if (!validTypes.contains(scheduleType)) {
            throw new InvalidScheduleException("Invalid schedule type: " + scheduleType);
        }
    }

    /**
     * Validate schedule status
     */
    private void validateScheduleStatus(String status) {
        List<String> validStatuses = List.of("PLANNED", "APPROVED", "CANCELLED", "COMPLETED");
        if (!validStatuses.contains(status)) {
            throw new InvalidScheduleException("Invalid schedule status: " + status);
        }
    }

    /**
     * Check for schedule conflicts
     */
    private boolean hasScheduleConflict(EmployeeSchedule currentSchedule, Instant newStartTime, Instant newEndTime) {
        List<EmployeeSchedule> conflictingSchedules = employeeScheduleRepository.findOverlappingSchedulesExcluding(
                newStartTime, newEndTime, currentSchedule.getId());
        return !conflictingSchedules.isEmpty();
    }

    // UTILITY methods

    /**
     * Check if employee schedule exists by ID
     */
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        log.debug("Checking if employee schedule exists with ID: {}", id);
        return employeeScheduleRepository.existsById(id);
    }

    /**
     * Get total employee schedule count
     */
    @Transactional(readOnly = true)
    public long getEmployeeScheduleCount() {
        log.debug("Getting total employee schedule count");
        return employeeScheduleRepository.count();
    }

    /**
     * Get employee schedule count by status
     */
    @Transactional(readOnly = true)
    public long getEmployeeScheduleCountByStatus(String status) {
        log.debug("Getting employee schedule count by status: {}", status);
        validateScheduleStatus(status);
        return employeeScheduleRepository.countByStatus(status);
    }

    /**
     * Get employee schedule count by type
     */
    @Transactional(readOnly = true)
    public long getEmployeeScheduleCountByType(String scheduleType) {
        log.debug("Getting employee schedule count by type: {}", scheduleType);
        validateScheduleType(scheduleType);
        return employeeScheduleRepository.countByScheduleType(scheduleType);
    }
}