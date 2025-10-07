package site.renzoproject.employee_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.mapper.EmployeeMapper;
import site.renzoproject.employee_service.mapper.EmployeeScheduleAssignmentMapper;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;
import site.renzoproject.employee_service.repository.EmployeeScheduleAssignmentRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeScheduleAssignmentService {

    private final EmployeeScheduleAssignmentRepository assignmentRepository;
    private final EmployeeScheduleAssignmentMapper assignmentMapper;
    private final EmployeeMapper employeeMapper;

    /**
     * Create a new employee schedule assignment
     */
    @Transactional
    public EmployeeScheduleAssignmentResponseDto createAssignment(EmployeeScheduleAssignmentRequestDto requestDto) {
        log.info("Creating new employee schedule assignment for employeeId: {}", requestDto.getEmployeeId());

        // Validate required fields
        if (requestDto.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        if (requestDto.getEmployeeScheduleId() == null) {
            throw new IllegalArgumentException("Employee schedule ID cannot be null");
        }

        // Check if assignment already exists
        if (assignmentRepository.existsByEmployeeIdAndScheduleId(
                requestDto.getEmployeeId(), requestDto.getEmployeeScheduleId())) {
            log.warn("Assignment already exists for employeeId: {} and scheduleId: {}",
                    requestDto.getEmployeeId(), requestDto.getEmployeeScheduleId());
            throw new RuntimeException("Assignment already exists for this employee and schedule");
        }

        EmployeeScheduleAssignment assignment = assignmentMapper.toEntity(requestDto);
        assignment.setId(UUID.randomUUID()); // Generate unique ID

        EmployeeScheduleAssignment savedAssignment = assignmentRepository.save(assignment);
        log.debug("Successfully created employee schedule assignment with id: {}", savedAssignment.getId());

        return assignmentMapper.toResponseDto(savedAssignment, employeeMapper);
    }

    /**
     * Get employee schedule assignment by ID
     */
    public EmployeeScheduleAssignmentResponseDto getAssignmentById(UUID id) {
        log.debug("Fetching employee schedule assignment with id: {}", id);

        EmployeeScheduleAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee schedule assignment not found with id: {}", id);
                    return new RuntimeException("Employee schedule assignment not found with id: " + id);
                });

        return assignmentMapper.toResponseDto(assignment, employeeMapper);
    }

    /**
     * Get all employee schedule assignments
     */
    public List<EmployeeScheduleAssignmentResponseDto> getAllAssignments() {
        log.debug("Fetching all employee schedule assignments");

        List<EmployeeScheduleAssignment> assignments = assignmentRepository.findAll();
        log.debug("Found {} employee schedule assignments", assignments.size());

        return assignmentMapper.toResponseDtoList(assignments, employeeMapper);
    }

    /**
     * Update an existing employee schedule assignment
     */
    @Transactional
    public EmployeeScheduleAssignmentResponseDto updateAssignment(UUID id, EmployeeScheduleAssignmentRequestDto requestDto) {
        log.info("Updating employee schedule assignment with id: {}", id);

        EmployeeScheduleAssignment existingAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee schedule assignment not found for update with id: {}", id);
                    return new RuntimeException("Employee schedule assignment not found with id: " + id);
                });

        // Update the existing entity with new data
        assignmentMapper.updateEntityFromDto(requestDto, existingAssignment);

        EmployeeScheduleAssignment updatedAssignment = assignmentRepository.save(existingAssignment);
        log.debug("Successfully updated employee schedule assignment with id: {}", id);

        return assignmentMapper.toResponseDto(updatedAssignment, employeeMapper);
    }

    /**
     * Delete an employee schedule assignment by ID
     */
    @Transactional
    public void deleteAssignment(UUID id) {
        log.info("Deleting employee schedule assignment with id: {}", id);

        if (!assignmentRepository.existsById(id)) {
            log.warn("Employee schedule assignment not found for deletion with id: {}", id);
            throw new RuntimeException("Employee schedule assignment not found with id: " + id);
        }

        assignmentRepository.deleteById(id);
        log.debug("Successfully deleted employee schedule assignment with id: {}", id);
    }

    /**
     * Check if employee schedule assignment exists by ID
     */
    public boolean assignmentExists(UUID id) {
        return assignmentRepository.existsById(id);
    }

    /**
     * Get assignments by employee ID
     */
    public List<EmployeeScheduleAssignmentResponseDto> getAssignmentsByEmployeeId(UUID employeeId) {
        log.debug("Fetching schedule assignments for employeeId: {}", employeeId);

        List<EmployeeScheduleAssignment> employeeAssignments = assignmentRepository.findByEmployeeId(employeeId);
        log.debug("Found {} schedule assignments for employeeId: {}", employeeAssignments.size(), employeeId);

        return assignmentMapper.toResponseDtoList(employeeAssignments, employeeMapper);
    }

    /**
     * Get assignments by schedule ID
     */
    public List<EmployeeScheduleAssignmentResponseDto> getAssignmentsByScheduleId(UUID scheduleId) {
        log.debug("Fetching assignments for scheduleId: {}", scheduleId);

        List<EmployeeScheduleAssignment> scheduleAssignments = assignmentRepository.findByScheduleId(scheduleId);
        log.debug("Found {} assignments for scheduleId: {}", scheduleAssignments.size(), scheduleId);

        return assignmentMapper.toResponseDtoList(scheduleAssignments, employeeMapper);
    }

    /**
     * Get assignments by role
     */
    public List<EmployeeScheduleAssignmentResponseDto> getAssignmentsByRole(String role) {
        log.debug("Fetching assignments for role: {}", role);

        List<EmployeeScheduleAssignment> roleAssignments = assignmentRepository.findByRole(role);
        log.debug("Found {} assignments for role: {}", roleAssignments.size(), role);

        return assignmentMapper.toResponseDtoList(roleAssignments, employeeMapper);
    }

    /**
     * Check if assignment exists for employee and schedule
     */
    public boolean assignmentExistsForEmployeeAndSchedule(UUID employeeId, UUID scheduleId) {
        return assignmentRepository.existsByEmployeeIdAndScheduleId(employeeId, scheduleId);
    }
}
