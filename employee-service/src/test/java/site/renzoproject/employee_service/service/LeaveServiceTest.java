package site.renzoproject.employee_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import site.renzoproject.employee_service.dto.LeavePage;
import site.renzoproject.employee_service.dto.LeaveRequestDto;
import site.renzoproject.employee_service.dto.LeaveResponseDto;
import site.renzoproject.employee_service.mapper.EmployeeMapper;
import site.renzoproject.employee_service.mapper.LeaveMapper;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.Leave;
import site.renzoproject.employee_service.repository.LeaveRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveServiceTest {

    @Mock
    private LeaveRepository leaveRepository;

    @Mock
    private LeaveMapper leaveMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private LeaveService leaveService;

    private UUID leaveId;
    private UUID employeeId;
    private LeaveRequestDto leaveRequestDto;
    private Leave leave;
    private LeaveResponseDto leaveResponseDto;
    private Employee employee;

    @BeforeEach
    void setUp() {
        leaveId = UUID.randomUUID();
        employeeId = UUID.randomUUID();

        employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .build();

        leaveRequestDto = LeaveRequestDto.builder()
                .employeeId(employeeId)
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status("REQUESTED")
                .reason("Family vacation")
                .build();

        leave = Leave.builder()
                .id(leaveId)
                .employee(employee)
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status("REQUESTED")
                .reason("Family vacation")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .modifiedBy("system")
                .build();

        leaveResponseDto = LeaveResponseDto.builder()
                .id(leaveId)
                .employeeId(employeeId)
                .employeeName("John Doe")
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status("REQUESTED")
                .reason("Family vacation")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .modifiedBy("system")
                .build();
    }

    @Test
    void createLeave_ValidRequest_ShouldReturnLeaveResponse() {
        // Arrange
        when(leaveMapper.toEntity(leaveRequestDto)).thenReturn(leave);
        when(leaveRepository.save(any(Leave.class))).thenReturn(leave);
        when(leaveMapper.toResponseDto(leave, employeeMapper)).thenReturn(leaveResponseDto);

        // Act
        LeaveResponseDto result = leaveService.createLeave(leaveRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(leaveId, result.getId());
        assertEquals(employeeId, result.getEmployeeId());
        assertEquals("VACATION", result.getLeaveType());
        assertEquals("REQUESTED", result.getStatus());

        verify(leaveMapper).toEntity(leaveRequestDto);
        verify(leaveRepository).save(any(Leave.class));
        verify(leaveMapper).toResponseDto(leave, employeeMapper);
    }

    @Test
    void createLeave_InvalidDateRange_ShouldThrowException() {
        // Arrange
        LeaveRequestDto invalidRequest = LeaveRequestDto.builder()
                .employeeId(employeeId)
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(5))
                .endDate(LocalDate.now().plusDays(1)) // End date before start date
                .reason("Invalid date range")
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> leaveService.createLeave(invalidRequest));
        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void createLeave_MissingRequiredFields_ShouldThrowException() {
        // Arrange
        LeaveRequestDto invalidRequest = LeaveRequestDto.builder()
                .employeeId(null) // Missing employee ID
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> leaveService.createLeave(invalidRequest));
        verify(leaveRepository, never()).save(any(Leave.class));
    }

    @Test
    void getLeaveById_ExistingId_ShouldReturnLeaveResponse() {
        // Arrange
        when(leaveRepository.findById(leaveId)).thenReturn(Optional.of(leave));
        when(leaveMapper.toResponseDto(leave, employeeMapper)).thenReturn(leaveResponseDto);

        // Act
        LeaveResponseDto result = leaveService.getLeaveById(leaveId);

        // Assert
        assertNotNull(result);
        assertEquals(leaveId, result.getId());
        verify(leaveRepository).findById(leaveId);
        verify(leaveMapper).toResponseDto(leave, employeeMapper);
    }

    @Test
    void getLeaveById_NonExistingId_ShouldThrowException() {
        // Arrange
        when(leaveRepository.findById(leaveId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> leaveService.getLeaveById(leaveId));
        verify(leaveRepository).findById(leaveId);
        verify(leaveMapper, never()).toResponseDto(any(), any());
    }

    @Test
    void getAllLeaves_ShouldReturnListOfLeaves() {
        // Arrange
        List<Leave> leaves = List.of(leave);
        List<LeaveResponseDto> expectedResponse = List.of(leaveResponseDto);

        when(leaveRepository.findAll()).thenReturn(leaves);
        when(leaveMapper.toResponseDtoList(leaves, employeeMapper)).thenReturn(expectedResponse);

        // Act
        List<LeaveResponseDto> result = leaveService.getAllLeaves();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(leaveResponseDto, result.get(0));
        verify(leaveRepository).findAll();
        verify(leaveMapper).toResponseDtoList(leaves, employeeMapper);
    }

    @Test
    void getAllLeavesWithPagination_ShouldReturnPaginatedLeaves() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Leave> leaves = List.of(leave);
        Page<Leave> leavePage = new PageImpl<>(leaves, pageable, 1);
        List<LeaveResponseDto> content = List.of(leaveResponseDto);

        when(leaveRepository.findAll(pageable)).thenReturn(leavePage);
        when(leaveMapper.toResponseDtoList(leaves, employeeMapper)).thenReturn(content);

        // Act
        LeavePage result = leaveService.getAllLeaves(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1, result.getTotalElements());
        verify(leaveRepository).findAll(pageable);
        verify(leaveMapper).toResponseDtoList(leaves, employeeMapper);
    }

    @Test
    void updateLeave_ExistingLeave_ShouldReturnUpdatedLeave() {
        // Arrange
        LeaveRequestDto updateRequest = LeaveRequestDto.builder()
                .employeeId(employeeId)
                .leaveType("SICK")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .status("APPROVED")
                .reason("Updated reason")
                .build();

        Leave updatedLeave = Leave.builder()
                .id(leaveId)
                .employee(employee)
                .leaveType("SICK")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .status("APPROVED")
                .reason("Updated reason")
                .build();

        LeaveResponseDto updatedResponse = LeaveResponseDto.builder()
                .id(leaveId)
                .employeeId(employeeId)
                .employeeName("John Doe")
                .leaveType("SICK")
                .startDate(LocalDate.now().plusDays(2))
                .endDate(LocalDate.now().plusDays(3))
                .status("APPROVED")
                .reason("Updated reason")
                .build();

        when(leaveRepository.findById(leaveId)).thenReturn(Optional.of(leave));
        when(leaveRepository.save(leave)).thenReturn(updatedLeave);
        when(leaveMapper.toResponseDto(updatedLeave, employeeMapper)).thenReturn(updatedResponse);

        // Act
        LeaveResponseDto result = leaveService.updateLeave(leaveId, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("SICK", result.getLeaveType());
        assertEquals("APPROVED", result.getStatus());
        verify(leaveRepository).findById(leaveId);
        verify(leaveMapper).updateEntityFromDto(updateRequest, leave);
        verify(leaveRepository).save(leave);
    }

    @Test
    void updateLeave_NonExistingLeave_ShouldThrowException() {
        // Arrange
        when(leaveRepository.findById(leaveId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> leaveService.updateLeave(leaveId, leaveRequestDto));
        verify(leaveRepository).findById(leaveId);
        verify(leaveRepository, never()).save(any());
    }

    @Test
    void deleteLeave_ExistingLeave_ShouldDeleteSuccessfully() {
        // Arrange
        when(leaveRepository.existsById(leaveId)).thenReturn(true);

        // Act
        leaveService.deleteLeave(leaveId);

        // Assert
        verify(leaveRepository).existsById(leaveId);
        verify(leaveRepository).deleteById(leaveId);
    }

    @Test
    void deleteLeave_NonExistingLeave_ShouldThrowException() {
        // Arrange
        when(leaveRepository.existsById(leaveId)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> leaveService.deleteLeave(leaveId));
        verify(leaveRepository).existsById(leaveId);
        verify(leaveRepository, never()).deleteById(any());
    }

    @Test
    void updateLeaveStatus_ValidStatus_ShouldUpdateSuccessfully() {
        // Arrange
        String newStatus = "APPROVED";
        Leave updatedLeave = Leave.builder()
                .id(leaveId)
                .employee(employee)
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status(newStatus)
                .reason("Family vacation")
                .build();

        LeaveResponseDto updatedResponse = LeaveResponseDto.builder()
                .id(leaveId)
                .employeeId(employeeId)
                .employeeName("John Doe")
                .leaveType("VACATION")
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status(newStatus)
                .reason("Family vacation")
                .build();

        when(leaveRepository.findById(leaveId)).thenReturn(Optional.of(leave));
        when(leaveRepository.save(leave)).thenReturn(updatedLeave);
        when(leaveMapper.toResponseDto(updatedLeave, employeeMapper)).thenReturn(updatedResponse);

        // Act
        LeaveResponseDto result = leaveService.updateLeaveStatus(leaveId, newStatus);

        // Assert
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(leaveRepository).findById(leaveId);
        verify(leaveRepository).save(leave);
    }

    @Test
    void updateLeaveStatus_InvalidStatus_ShouldThrowException() {
        // Arrange
        String invalidStatus = "INVALID_STATUS";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> leaveService.updateLeaveStatus(leaveId, invalidStatus));
        verify(leaveRepository, never()).findById(any());
        verify(leaveRepository, never()).save(any());
    }

    @Test
    void getLeavesByEmployeeId_ShouldReturnEmployeeLeaves() {
        // Arrange
        List<Leave> employeeLeaves = List.of(leave);
        List<LeaveResponseDto> expectedResponse = List.of(leaveResponseDto);

        when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(employeeLeaves);
        when(leaveMapper.toResponseDtoList(employeeLeaves, employeeMapper)).thenReturn(expectedResponse);

        // Act
        List<LeaveResponseDto> result = leaveService.getLeavesByEmployeeId(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(leaveRepository).findByEmployeeId(employeeId);
        verify(leaveMapper).toResponseDtoList(employeeLeaves, employeeMapper);
    }

    @Test
    void getLeavesByStatus_ValidStatus_ShouldReturnFilteredLeaves() {
        // Arrange
        String status = "APPROVED";
        List<Leave> statusLeaves = List.of(leave);
        List<LeaveResponseDto> expectedResponse = List.of(leaveResponseDto);

        when(leaveRepository.findByStatus(status)).thenReturn(statusLeaves);
        when(leaveMapper.toResponseDtoList(statusLeaves, employeeMapper)).thenReturn(expectedResponse);

        // Act
        List<LeaveResponseDto> result = leaveService.getLeavesByStatus(status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(leaveRepository).findByStatus(status);
        verify(leaveMapper).toResponseDtoList(statusLeaves, employeeMapper);
    }

    @Test
    void getLeavesByStatus_InvalidStatus_ShouldThrowException() {
        // Arrange
        String invalidStatus = "INVALID_STATUS";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> leaveService.getLeavesByStatus(invalidStatus));
        verify(leaveRepository, never()).findByStatus(any());
    }

    @Test
    void getLeavesByDateRange_ValidRange_ShouldReturnLeaves() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate endDate = LocalDate.now().plusDays(10);
        List<Leave> rangeLeaves = List.of(leave);
        List<LeaveResponseDto> expectedResponse = List.of(leaveResponseDto);

        when(leaveRepository.findByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate))
                .thenReturn(rangeLeaves);
        when(leaveMapper.toResponseDtoList(rangeLeaves, employeeMapper)).thenReturn(expectedResponse);

        // Act
        List<LeaveResponseDto> result = leaveService.getLeavesByDateRange(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(leaveRepository).findByStartDateBetweenOrEndDateBetween(startDate, endDate, startDate, endDate);
    }

    @Test
    void getLeavesByDateRange_InvalidRange_ShouldThrowException() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(10);
        LocalDate endDate = LocalDate.now().minusDays(10); // Invalid range

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> leaveService.getLeavesByDateRange(startDate, endDate));
        verify(leaveRepository, never()).findByStartDateBetweenOrEndDateBetween(any(), any(), any(), any());
    }

    @Test
    void getOverlappingLeaves_ShouldReturnOverlappingLeaves() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        List<Leave> overlappingLeaves = List.of(leave);
        List<LeaveResponseDto> expectedResponse = List.of(leaveResponseDto);

        when(leaveRepository.findOverlappingLeaves(employeeId, startDate, endDate)).thenReturn(overlappingLeaves);
        when(leaveMapper.toResponseDtoList(overlappingLeaves, employeeMapper)).thenReturn(expectedResponse);

        // Act
        List<LeaveResponseDto> result = leaveService.getOverlappingLeaves(employeeId, startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(leaveRepository).findOverlappingLeaves(employeeId, startDate, endDate);
    }

    @Test
    void leaveExists_ExistingLeave_ShouldReturnTrue() {
        // Arrange
        when(leaveRepository.existsById(leaveId)).thenReturn(true);

        // Act
        boolean result = leaveService.leaveExists(leaveId);

        // Assert
        assertTrue(result);
        verify(leaveRepository).existsById(leaveId);
    }

    @Test
    void leaveExists_NonExistingLeave_ShouldReturnFalse() {
        // Arrange
        when(leaveRepository.existsById(leaveId)).thenReturn(false);

        // Act
        boolean result = leaveService.leaveExists(leaveId);

        // Assert
        assertFalse(result);
        verify(leaveRepository).existsById(leaveId);
    }

    @Test
    void hasOverlappingLeave_WithOverlap_ShouldReturnTrue() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(5);
        UUID excludeLeaveId = UUID.randomUUID();
        List<Leave> overlappingLeaves = List.of(leave);

        when(leaveRepository.findOverlappingLeavesExcludingId(employeeId, startDate, endDate, excludeLeaveId))
                .thenReturn(overlappingLeaves);

        // Act
        boolean result = leaveService.hasOverlappingLeave(employeeId, startDate, endDate, excludeLeaveId);

        // Assert
        assertTrue(result);
        verify(leaveRepository).findOverlappingLeavesExcludingId(employeeId, startDate, endDate, excludeLeaveId);
    }

    @Test
    void hasOverlappingLeave_NoOverlap_ShouldReturnFalse() {
        // Arrange
        LocalDate startDate = LocalDate.now().plusDays(10);
        LocalDate endDate = LocalDate.now().plusDays(15);
        UUID excludeLeaveId = UUID.randomUUID();

        when(leaveRepository.findOverlappingLeavesExcludingId(employeeId, startDate, endDate, excludeLeaveId))
                .thenReturn(List.of());

        // Act
        boolean result = leaveService.hasOverlappingLeave(employeeId, startDate, endDate, excludeLeaveId);

        // Assert
        assertFalse(result);
        verify(leaveRepository).findOverlappingLeavesExcludingId(employeeId, startDate, endDate, excludeLeaveId);
    }
}