package site.renzoproject.employee_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleAssignmentResponseDto;
import site.renzoproject.employee_service.mapper.EmployeeMapper;
import site.renzoproject.employee_service.mapper.EmployeeScheduleAssignmentMapper;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;
import site.renzoproject.employee_service.repository.EmployeeScheduleAssignmentRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeScheduleAssignmentServiceTest {

    @Mock
    private EmployeeScheduleAssignmentRepository assignmentRepository;

    @Mock
    private EmployeeScheduleAssignmentMapper assignmentMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeScheduleAssignmentService assignmentService;

    private UUID assignmentId;
    private UUID employeeId;
    private UUID scheduleId;
    private EmployeeScheduleAssignmentRequestDto requestDto;
    private EmployeeScheduleAssignment assignment;
    private EmployeeScheduleAssignmentResponseDto responseDto;

    @BeforeEach
    void setUp() {
        assignmentId = UUID.randomUUID();
        employeeId = UUID.randomUUID();
        scheduleId = UUID.randomUUID();

        requestDto = EmployeeScheduleAssignmentRequestDto.builder()
                .employeeId(employeeId)
                .employeeScheduleId(scheduleId)
                .assignedAt(Instant.now())
                .role("Trainer")
                .build();

        assignment = EmployeeScheduleAssignment.builder()
                .id(assignmentId)
                .assignedAt(Instant.now())
                .role("Trainer")
                .build();

        responseDto = EmployeeScheduleAssignmentResponseDto.builder()
                .id(assignmentId)
                .employeeId(employeeId)
                .scheduleId(scheduleId)
                .assignedAt(Instant.now())
                .role("Trainer")
                .build();
    }

    @Test
    void createAssignment_ShouldCreateSuccessfully() {
        // Arrange
        when(assignmentRepository.existsByEmployeeIdAndScheduleId(employeeId, scheduleId))
                .thenReturn(false);
        when(assignmentMapper.toEntity(requestDto)).thenReturn(assignment);
        when(assignmentRepository.save(any(EmployeeScheduleAssignment.class))).thenReturn(assignment);
        when(assignmentMapper.toResponseDto(assignment, employeeMapper)).thenReturn(responseDto);

        // Act
        EmployeeScheduleAssignmentResponseDto result = assignmentService.createAssignment(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(assignmentId, result.getId());
        assertEquals("Trainer", result.getRole());
        verify(assignmentRepository).save(assignment);
        verify(assignmentMapper).toResponseDto(assignment, employeeMapper);
    }

    @Test
    void createAssignment_WhenAssignmentExists_ShouldThrowException() {
        // Arrange
        when(assignmentRepository.existsByEmployeeIdAndScheduleId(employeeId, scheduleId))
                .thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> assignmentService.createAssignment(requestDto));

        assertEquals("Assignment already exists for this employee and schedule", exception.getMessage());
        verify(assignmentRepository, never()).save(any());
    }

    @Test
    void createAssignment_WhenEmployeeIdNull_ShouldThrowException() {
        // Arrange
        requestDto.setEmployeeId(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> assignmentService.createAssignment(requestDto));

        assertEquals("Employee ID cannot be null", exception.getMessage());
    }

    @Test
    void getAssignmentById_ShouldReturnAssignment() {
        // Arrange
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(assignmentMapper.toResponseDto(assignment, employeeMapper)).thenReturn(responseDto);

        // Act
        EmployeeScheduleAssignmentResponseDto result = assignmentService.getAssignmentById(assignmentId);

        // Assert
        assertNotNull(result);
        assertEquals(assignmentId, result.getId());
        verify(assignmentRepository).findById(assignmentId);
    }

    @Test
    void getAssignmentById_WhenNotFound_ShouldThrowException() {
        // Arrange
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> assignmentService.getAssignmentById(assignmentId));

        assertEquals("Employee schedule assignment not found with id: " + assignmentId, exception.getMessage());
    }

    @Test
    void getAllAssignments_ShouldReturnAllAssignments() {
        // Arrange
        List<EmployeeScheduleAssignment> assignments = List.of(assignment);
        List<EmployeeScheduleAssignmentResponseDto> responseDtos = List.of(responseDto);

        when(assignmentRepository.findAll()).thenReturn(assignments);
        when(assignmentMapper.toResponseDtoList(assignments, employeeMapper)).thenReturn(responseDtos);

        // Act
        List<EmployeeScheduleAssignmentResponseDto> result = assignmentService.getAllAssignments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository).findAll();
    }

    @Test
    void updateAssignment_ShouldUpdateSuccessfully() {
        // Arrange
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(assignment)).thenReturn(assignment);
        when(assignmentMapper.toResponseDto(assignment, employeeMapper)).thenReturn(responseDto);

        // Act
        EmployeeScheduleAssignmentResponseDto result = assignmentService.updateAssignment(assignmentId, requestDto);

        // Assert
        assertNotNull(result);
        verify(assignmentMapper).updateEntityFromDto(requestDto, assignment);
        verify(assignmentRepository).save(assignment);
    }

    @Test
    void updateAssignment_WhenNotFound_ShouldThrowException() {
        // Arrange
        when(assignmentRepository.findById(assignmentId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> assignmentService.updateAssignment(assignmentId, requestDto));

        assertEquals("Employee schedule assignment not found with id: " + assignmentId, exception.getMessage());
        verify(assignmentRepository, never()).save(any());
    }

    @Test
    void deleteAssignment_ShouldDeleteSuccessfully() {
        // Arrange
        when(assignmentRepository.existsById(assignmentId)).thenReturn(true);

        // Act
        assignmentService.deleteAssignment(assignmentId);

        // Assert
        verify(assignmentRepository).deleteById(assignmentId);
    }

    @Test
    void deleteAssignment_WhenNotFound_ShouldThrowException() {
        // Arrange
        when(assignmentRepository.existsById(assignmentId)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> assignmentService.deleteAssignment(assignmentId));

        assertEquals("Employee schedule assignment not found with id: " + assignmentId, exception.getMessage());
        verify(assignmentRepository, never()).deleteById(any());
    }

    @Test
    void assignmentExists_ShouldReturnTrue() {
        // Arrange
        when(assignmentRepository.existsById(assignmentId)).thenReturn(true);

        // Act
        boolean result = assignmentService.assignmentExists(assignmentId);

        // Assert
        assertTrue(result);
        verify(assignmentRepository).existsById(assignmentId);
    }

    @Test
    void getAssignmentsByEmployeeId_ShouldReturnAssignments() {
        // Arrange
        List<EmployeeScheduleAssignment> assignments = List.of(assignment);
        List<EmployeeScheduleAssignmentResponseDto> responseDtos = List.of(responseDto);

        when(assignmentRepository.findByEmployeeId(employeeId)).thenReturn(assignments);
        when(assignmentMapper.toResponseDtoList(assignments, employeeMapper)).thenReturn(responseDtos);

        // Act
        List<EmployeeScheduleAssignmentResponseDto> result = assignmentService.getAssignmentsByEmployeeId(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository).findByEmployeeId(employeeId);
    }

    @Test
    void getAssignmentsByScheduleId_ShouldReturnAssignments() {
        // Arrange
        List<EmployeeScheduleAssignment> assignments = List.of(assignment);
        List<EmployeeScheduleAssignmentResponseDto> responseDtos = List.of(responseDto);

        when(assignmentRepository.findByScheduleId(scheduleId)).thenReturn(assignments);
        when(assignmentMapper.toResponseDtoList(assignments, employeeMapper)).thenReturn(responseDtos);

        // Act
        List<EmployeeScheduleAssignmentResponseDto> result = assignmentService.getAssignmentsByScheduleId(scheduleId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository).findByScheduleId(scheduleId);
    }

    @Test
    void getAssignmentsByRole_ShouldReturnAssignments() {
        // Arrange
        String role = "Trainer";
        List<EmployeeScheduleAssignment> assignments = List.of(assignment);
        List<EmployeeScheduleAssignmentResponseDto> responseDtos = List.of(responseDto);

        when(assignmentRepository.findByRole(role)).thenReturn(assignments);
        when(assignmentMapper.toResponseDtoList(assignments, employeeMapper)).thenReturn(responseDtos);

        // Act
        List<EmployeeScheduleAssignmentResponseDto> result = assignmentService.getAssignmentsByRole(role);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(assignmentRepository).findByRole(role);
    }

    @Test
    void assignmentExistsForEmployeeAndSchedule_ShouldReturnTrue() {
        // Arrange
        when(assignmentRepository.existsByEmployeeIdAndScheduleId(employeeId, scheduleId))
                .thenReturn(true);

        // Act
        boolean result = assignmentService.assignmentExistsForEmployeeAndSchedule(employeeId, scheduleId);

        // Assert
        assertTrue(result);
        verify(assignmentRepository).existsByEmployeeIdAndScheduleId(employeeId, scheduleId);
    }
}