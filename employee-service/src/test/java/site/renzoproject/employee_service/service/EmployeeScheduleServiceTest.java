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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EmployeeScheduleServiceTest {

    @Mock
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Mock
    private EmployeeScheduleMapper employeeScheduleMapper;

    @InjectMocks
    private EmployeeScheduleService employeeScheduleService;

    private UUID scheduleId;
    private EmployeeSchedule employeeSchedule;
    private EmployeeScheduleRequestDto employeeScheduleRequestDto;
    private EmployeeScheduleResponseDto employeeScheduleResponseDto;
    private EmployeeScheduleSummaryDto employeeScheduleSummaryDto;

    @BeforeEach
    void setUp() {
        scheduleId = UUID.randomUUID();
        Instant startTime = Instant.now().plusSeconds(3600); // 1 hour from now
        Instant endTime = Instant.now().plusSeconds(7200);   // 2 hours from now

        employeeSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .modifiedBy("system")
                .build();

        employeeScheduleRequestDto = EmployeeScheduleRequestDto.builder()
                .description("Morning Shift")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();

        employeeScheduleResponseDto = EmployeeScheduleResponseDto.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .modifiedBy("system")
                .build();

        employeeScheduleSummaryDto = EmployeeScheduleSummaryDto.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();
    }

    // CREATE operations tests

    @Test
    void createEmployeeSchedule_Success() {
        // Given
        when(employeeScheduleMapper.toEntity(employeeScheduleRequestDto)).thenReturn(employeeSchedule);
        when(employeeScheduleRepository.save(any(EmployeeSchedule.class))).thenReturn(employeeSchedule);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.createEmployeeSchedule(employeeScheduleRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(scheduleId, result.getId());
        assertEquals("Morning Shift", result.getDescription());
        assertEquals("SHIFT", result.getScheduleType());
        assertEquals("PLANNED", result.getStatus());

        verify(employeeScheduleMapper).toEntity(employeeScheduleRequestDto);
        verify(employeeScheduleRepository).save(any(EmployeeSchedule.class));
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void createEmployeeSchedule_WithDefaultValues_Success() {
        // Given
        EmployeeScheduleRequestDto requestWithoutDefaults = EmployeeScheduleRequestDto.builder()
                .description("Test Schedule")
                .startTime(Instant.now().plusSeconds(3600))
                .endTime(Instant.now().plusSeconds(7200))
                .build();

        EmployeeSchedule scheduleWithDefaults = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Test Schedule")
                .startTime(requestWithoutDefaults.getStartTime())
                .endTime(requestWithoutDefaults.getEndTime())
                .scheduleType("SHIFT") // Default value
                .status("PLANNED") // Default value
                .build();

        when(employeeScheduleMapper.toEntity(requestWithoutDefaults)).thenReturn(scheduleWithDefaults);
        when(employeeScheduleRepository.save(any(EmployeeSchedule.class))).thenReturn(scheduleWithDefaults);
        when(employeeScheduleMapper.toResponseDto(scheduleWithDefaults)).thenReturn(employeeScheduleResponseDto);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.createEmployeeSchedule(requestWithoutDefaults);

        // Then
        assertNotNull(result);
        verify(employeeScheduleRepository).save(any(EmployeeSchedule.class));
    }

    @Test
    void createEmployeeSchedule_WithInvalidTimeRange_ThrowsException() {
        // Given
        EmployeeScheduleRequestDto invalidRequest = EmployeeScheduleRequestDto.builder()
                .description("Invalid Schedule")
                .startTime(Instant.now().plusSeconds(7200)) // Later time
                .endTime(Instant.now().plusSeconds(3600))   // Earlier time
                .build();

        // When & Then
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.createEmployeeSchedule(invalidRequest));

        verify(employeeScheduleRepository, never()).save(any(EmployeeSchedule.class));
    }

    @Test
    void createEmployeeSchedule_WithPastStartTime_ThrowsException() {
        // Given
        EmployeeScheduleRequestDto pastRequest = EmployeeScheduleRequestDto.builder()
                .description("Past Schedule")
                .startTime(Instant.now().minusSeconds(3600)) // Past time
                .endTime(Instant.now().plusSeconds(3600))
                .build();

        // When & Then
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.createEmployeeSchedule(pastRequest));

        verify(employeeScheduleRepository, never()).save(any(EmployeeSchedule.class));
    }

    @Test
    void createEmployeeSchedule_WithInvalidScheduleType_ThrowsException() {
        // Given
        EmployeeScheduleRequestDto invalidTypeRequest = EmployeeScheduleRequestDto.builder()
                .description("Invalid Type Schedule")
                .startTime(Instant.now().plusSeconds(3600))
                .endTime(Instant.now().plusSeconds(7200))
                .scheduleType("INVALID_TYPE")
                .build();

        // When & Then
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.createEmployeeSchedule(invalidTypeRequest));

        verify(employeeScheduleRepository, never()).save(any(EmployeeSchedule.class));
    }

    @Test
    void bulkCreateEmployeeSchedules_Success() {
        // Given
        List<EmployeeScheduleRequestDto> requestDtos = List.of(employeeScheduleRequestDto, employeeScheduleRequestDto);
        List<EmployeeSchedule> schedules = List.of(employeeSchedule, employeeSchedule);

        when(employeeScheduleMapper.toEntity(employeeScheduleRequestDto)).thenReturn(employeeSchedule);
        when(employeeScheduleRepository.saveAll(any(List.class))).thenReturn(schedules);

        // When
        employeeScheduleService.bulkCreateEmployeeSchedules(requestDtos);

        // Then
        verify(employeeScheduleMapper, times(2)).toEntity(employeeScheduleRequestDto);
        verify(employeeScheduleRepository).saveAll(any(List.class));
    }

    // READ operations tests

    @Test
    void getEmployeeScheduleById_Success() {
        // Given
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.getEmployeeScheduleById(scheduleId);

        // Then
        assertNotNull(result);
        assertEquals(scheduleId, result.getId());
        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getEmployeeScheduleById_WhenNotFound_ThrowsException() {
        // Given
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EmployeeScheduleNotFoundException.class,
                () -> employeeScheduleService.getEmployeeScheduleById(scheduleId));

        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleMapper, never()).toResponseDto(any(EmployeeSchedule.class));
    }

    @Test
    void getAllEmployeeSchedules_WithPagination_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);
        Page<EmployeeSchedule> schedulePage = new PageImpl<>(schedules, pageable, 1);

        when(employeeScheduleRepository.findAll(pageable)).thenReturn(schedulePage);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        EmployeeSchedulePage result = employeeScheduleService.getAllEmployeeSchedules(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(employeeScheduleRepository).findAll(pageable);
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getAllEmployeeSchedules_WithoutPagination_Success() {
        // Given
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);
        when(employeeScheduleRepository.findAll()).thenReturn(schedules);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        List<EmployeeScheduleResponseDto> result = employeeScheduleService.getAllEmployeeSchedules();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findAll();
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getEmployeeSchedulesByType_Success() {
        // Given
        String scheduleType = "SHIFT";
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);
        when(employeeScheduleRepository.findByScheduleType(scheduleType)).thenReturn(schedules);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        List<EmployeeScheduleResponseDto> result = employeeScheduleService.getEmployeeSchedulesByType(scheduleType);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findByScheduleType(scheduleType);
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getEmployeeSchedulesByStatus_Success() {
        // Given
        String status = "PLANNED";
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);
        when(employeeScheduleRepository.findByStatus(status)).thenReturn(schedules);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        List<EmployeeScheduleResponseDto> result = employeeScheduleService.getEmployeeSchedulesByStatus(status);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findByStatus(status);
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getEmployeeSchedulesByDateRange_Success() {
        // Given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(7);
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);

        when(employeeScheduleRepository.findByStartTimeBetween(any(Instant.class), any(Instant.class)))
                .thenReturn(schedules);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        List<EmployeeScheduleResponseDto> result = employeeScheduleService.getEmployeeSchedulesByDateRange(startDate, endDate);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findByStartTimeBetween(any(Instant.class), any(Instant.class));
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getActiveEmployeeSchedules_Success() {
        // Given
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);
        when(employeeScheduleRepository.findByStatusNot("CANCELLED")).thenReturn(schedules);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        List<EmployeeScheduleResponseDto> result = employeeScheduleService.getActiveEmployeeSchedules();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findByStatusNot("CANCELLED");
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    @Test
    void getEmployeeSchedulesSummary_Success() {
        // Given
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);
        when(employeeScheduleRepository.findAll()).thenReturn(schedules);
        when(employeeScheduleMapper.toSummaryDto(employeeSchedule)).thenReturn(employeeScheduleSummaryDto);

        // When
        List<EmployeeScheduleSummaryDto> result = employeeScheduleService.getEmployeeSchedulesSummary();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findAll();
        verify(employeeScheduleMapper).toSummaryDto(employeeSchedule);
    }

    @Test
    void getUpcomingEmployeeSchedules_Success() {
        // Given
        int days = 7;
        List<EmployeeSchedule> schedules = List.of(employeeSchedule);

        when(employeeScheduleRepository.findByStartTimeBetweenAndStatusNot(any(Instant.class), any(Instant.class), eq("CANCELLED")))
                .thenReturn(schedules);
        when(employeeScheduleMapper.toResponseDto(employeeSchedule)).thenReturn(employeeScheduleResponseDto);

        // When
        List<EmployeeScheduleResponseDto> result = employeeScheduleService.getUpcomingEmployeeSchedules(days);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeScheduleRepository).findByStartTimeBetweenAndStatusNot(any(Instant.class), any(Instant.class), eq("CANCELLED"));
        verify(employeeScheduleMapper).toResponseDto(employeeSchedule);
    }

    // UPDATE operations tests

    @Test
    void updateEmployeeSchedule_Success() {
        // Given
        EmployeeScheduleRequestDto updateRequest = EmployeeScheduleRequestDto.builder()
                .description("Updated Morning Shift")
                .startTime(Instant.now().plusSeconds(3600))
                .endTime(Instant.now().plusSeconds(7200))
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        EmployeeSchedule updatedSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Updated Morning Shift")
                .startTime(updateRequest.getStartTime())
                .endTime(updateRequest.getEndTime())
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        EmployeeScheduleResponseDto updatedResponse = EmployeeScheduleResponseDto.builder()
                .id(scheduleId)
                .description("Updated Morning Shift")
                .startTime(updateRequest.getStartTime())
                .endTime(updateRequest.getEndTime())
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));
        when(employeeScheduleRepository.save(employeeSchedule)).thenReturn(updatedSchedule);
        when(employeeScheduleMapper.toResponseDto(updatedSchedule)).thenReturn(updatedResponse);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.updateEmployeeSchedule(scheduleId, updateRequest);

        // Then
        assertNotNull(result);
        assertEquals("Updated Morning Shift", result.getDescription());
        assertEquals("APPROVED", result.getStatus());

        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleMapper).updateEntityFromDto(updateRequest, employeeSchedule);
        verify(employeeScheduleRepository).save(employeeSchedule);
        verify(employeeScheduleMapper).toResponseDto(updatedSchedule);
    }

    @Test
    void updateEmployeeScheduleStatus_Success() {
        // Given
        String newStatus = "APPROVED";
        EmployeeSchedule updatedSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(employeeSchedule.getStartTime())
                .endTime(employeeSchedule.getEndTime())
                .scheduleType("SHIFT")
                .status(newStatus)
                .build();

        EmployeeScheduleResponseDto updatedResponse = EmployeeScheduleResponseDto.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(employeeSchedule.getStartTime())
                .endTime(employeeSchedule.getEndTime())
                .scheduleType("SHIFT")
                .status(newStatus)
                .build();

        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));
        when(employeeScheduleRepository.save(employeeSchedule)).thenReturn(updatedSchedule);
        when(employeeScheduleMapper.toResponseDto(updatedSchedule)).thenReturn(updatedResponse);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.updateEmployeeScheduleStatus(scheduleId, newStatus);

        // Then
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleRepository).save(employeeSchedule);
        verify(employeeScheduleMapper).toResponseDto(updatedSchedule);
    }

    @Test
    void approveEmployeeSchedule_Success() {
        // Given
        EmployeeSchedule approvedSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(employeeSchedule.getStartTime())
                .endTime(employeeSchedule.getEndTime())
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        EmployeeScheduleResponseDto approvedResponse = EmployeeScheduleResponseDto.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(employeeSchedule.getStartTime())
                .endTime(employeeSchedule.getEndTime())
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));
        when(employeeScheduleRepository.save(employeeSchedule)).thenReturn(approvedSchedule);
        when(employeeScheduleMapper.toResponseDto(approvedSchedule)).thenReturn(approvedResponse);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.approveEmployeeSchedule(scheduleId);

        // Then
        assertNotNull(result);
        assertEquals("APPROVED", result.getStatus());
        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleRepository).save(employeeSchedule);
        verify(employeeScheduleMapper).toResponseDto(approvedSchedule);
    }

    @Test
    void approveEmployeeSchedule_WhenCancelled_ThrowsException() {
        // Given
        employeeSchedule.setStatus("CANCELLED");
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));

        // When & Then
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.approveEmployeeSchedule(scheduleId));

        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleRepository, never()).save(any(EmployeeSchedule.class));
    }

    @Test
    void cancelEmployeeSchedule_Success() {
        // Given
        String reason = "Employee unavailable";
        EmployeeSchedule cancelledSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Shift [CANCELLED: Employee unavailable]")
                .startTime(employeeSchedule.getStartTime())
                .endTime(employeeSchedule.getEndTime())
                .scheduleType("SHIFT")
                .status("CANCELLED")
                .build();

        EmployeeScheduleResponseDto cancelledResponse = EmployeeScheduleResponseDto.builder()
                .id(scheduleId)
                .description("Morning Shift [CANCELLED: Employee unavailable]")
                .startTime(employeeSchedule.getStartTime())
                .endTime(employeeSchedule.getEndTime())
                .scheduleType("SHIFT")
                .status("CANCELLED")
                .build();

        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));
        when(employeeScheduleRepository.save(employeeSchedule)).thenReturn(cancelledSchedule);
        when(employeeScheduleMapper.toResponseDto(cancelledSchedule)).thenReturn(cancelledResponse);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.cancelEmployeeSchedule(scheduleId, reason);

        // Then
        assertNotNull(result);
        assertEquals("CANCELLED", result.getStatus());
        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleRepository).save(employeeSchedule);
        verify(employeeScheduleMapper).toResponseDto(cancelledSchedule);
    }

    @Test
    void rescheduleEmployeeSchedule_Success() {
        // Given
        Instant newStartTime = Instant.now().plusSeconds(10800); // 3 hours from now
        Instant newEndTime = Instant.now().plusSeconds(14400);   // 4 hours from now

        EmployeeSchedule rescheduledSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(newStartTime)
                .endTime(newEndTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();

        EmployeeScheduleResponseDto rescheduledResponse = EmployeeScheduleResponseDto.builder()
                .id(scheduleId)
                .description("Morning Shift")
                .startTime(newStartTime)
                .endTime(newEndTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();

        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(employeeSchedule));
        when(employeeScheduleRepository.findOverlappingSchedulesExcluding(any(Instant.class), any(Instant.class), any(UUID.class)))
                .thenReturn(List.of());
        when(employeeScheduleRepository.save(employeeSchedule)).thenReturn(rescheduledSchedule);
        when(employeeScheduleMapper.toResponseDto(rescheduledSchedule)).thenReturn(rescheduledResponse);

        // When
        EmployeeScheduleResponseDto result = employeeScheduleService.rescheduleEmployeeSchedule(scheduleId, newStartTime, newEndTime);

        // Then
        assertNotNull(result);
        assertEquals(newStartTime, result.getStartTime());
        assertEquals(newEndTime, result.getEndTime());
        verify(employeeScheduleRepository).findById(scheduleId);
        verify(employeeScheduleRepository).findOverlappingSchedulesExcluding(any(Instant.class), any(Instant.class), any(UUID.class));
        verify(employeeScheduleRepository).save(employeeSchedule);
        verify(employeeScheduleMapper).toResponseDto(rescheduledSchedule);
    }

    // DELETE operations tests

    @Test
    void deleteEmployeeSchedule_Success() {
        // Given
        when(employeeScheduleRepository.existsById(scheduleId)).thenReturn(true);

        // When
        employeeScheduleService.deleteEmployeeSchedule(scheduleId);

        // Then
        verify(employeeScheduleRepository).existsById(scheduleId);
        verify(employeeScheduleRepository).deleteById(scheduleId);
    }

    @Test
    void deleteEmployeeSchedule_WhenNotFound_ThrowsException() {
        // Given
        when(employeeScheduleRepository.existsById(scheduleId)).thenReturn(false);

        // When & Then
        assertThrows(EmployeeScheduleNotFoundException.class,
                () -> employeeScheduleService.deleteEmployeeSchedule(scheduleId));

        verify(employeeScheduleRepository).existsById(scheduleId);
        verify(employeeScheduleRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void deleteEmployeeSchedulesByStatus_Success() {
        // Given
        String status = "CANCELLED";
        when(employeeScheduleRepository.deleteByStatus(status)).thenReturn(5L);

        // When
        long deletedCount = employeeScheduleService.deleteEmployeeSchedulesByStatus(status);

        // Then
        assertEquals(5L, deletedCount);
        verify(employeeScheduleRepository).deleteByStatus(status);
    }

    // UTILITY methods tests

    @Test
    void existsById_ReturnsTrue() {
        // Given
        when(employeeScheduleRepository.existsById(scheduleId)).thenReturn(true);

        // When
        boolean result = employeeScheduleService.existsById(scheduleId);

        // Then
        assertTrue(result);
        verify(employeeScheduleRepository).existsById(scheduleId);
    }

    @Test
    void existsById_ReturnsFalse() {
        // Given
        when(employeeScheduleRepository.existsById(scheduleId)).thenReturn(false);

        // When
        boolean result = employeeScheduleService.existsById(scheduleId);

        // Then
        assertFalse(result);
        verify(employeeScheduleRepository).existsById(scheduleId);
    }

    @Test
    void getEmployeeScheduleCount_Success() {
        // Given
        when(employeeScheduleRepository.count()).thenReturn(10L);

        // When
        long count = employeeScheduleService.getEmployeeScheduleCount();

        // Then
        assertEquals(10L, count);
        verify(employeeScheduleRepository).count();
    }

    @Test
    void getEmployeeScheduleCountByStatus_Success() {
        // Given
        String status = "PLANNED";
        when(employeeScheduleRepository.countByStatus(status)).thenReturn(5L);

        // When
        long count = employeeScheduleService.getEmployeeScheduleCountByStatus(status);

        // Then
        assertEquals(5L, count);
        verify(employeeScheduleRepository).countByStatus(status);
    }

    @Test
    void getEmployeeScheduleCountByType_Success() {
        // Given
        String scheduleType = "SHIFT";
        when(employeeScheduleRepository.countByScheduleType(scheduleType)).thenReturn(8L);

        // When
        long count = employeeScheduleService.getEmployeeScheduleCountByType(scheduleType);

        // Then
        assertEquals(8L, count);
        verify(employeeScheduleRepository).countByScheduleType(scheduleType);
    }
}