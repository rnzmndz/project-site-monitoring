package site.renzoproject.employee_service.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.EmployeeScheduleRequestDto;
import site.renzoproject.employee_service.exception.InvalidScheduleException;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.service.EmployeeScheduleService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeScheduleServiceEdgeCasesTest {

    @Mock
    private EmployeeScheduleRepository employeeScheduleRepository;

    @InjectMocks
    private EmployeeScheduleService employeeScheduleService;

    @Test
    void createEmployeeSchedule_WithNullRequest_ThrowsException() {
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.createEmployeeSchedule(null));
    }

    @Test
    void createEmployeeSchedule_WithNullStartTime_ThrowsException() {
        EmployeeScheduleRequestDto request = EmployeeScheduleRequestDto.builder()
                .description("Test Schedule")
                .startTime(null)
                .endTime(Instant.now().plusSeconds(3600))
                .build();

        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.createEmployeeSchedule(request));
    }

    @Test
    void createEmployeeSchedule_WithNullEndTime_ThrowsException() {
        EmployeeScheduleRequestDto request = EmployeeScheduleRequestDto.builder()
                .description("Test Schedule")
                .startTime(Instant.now().plusSeconds(3600))
                .endTime(null)
                .build();

        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.createEmployeeSchedule(request));
    }

    @Test
    void getEmployeeSchedulesByType_WithInvalidType_ThrowsException() {
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.getEmployeeSchedulesByType("INVALID_TYPE"));
    }

    @Test
    void getEmployeeSchedulesByStatus_WithInvalidStatus_ThrowsException() {
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.getEmployeeSchedulesByStatus("INVALID_STATUS"));
    }

    @Test
    void rescheduleEmployeeSchedule_WithConflict_ThrowsException() {
        // Given
        UUID scheduleId = UUID.randomUUID();
        Instant newStartTime = Instant.now().plusSeconds(3600);
        Instant newEndTime = Instant.now().plusSeconds(7200);

        EmployeeSchedule existingSchedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Existing Schedule")
                .startTime(newStartTime)
                .endTime(newEndTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();

        EmployeeSchedule conflictingSchedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Conflicting Schedule")
                .startTime(newStartTime)
                .endTime(newEndTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();

        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(java.util.Optional.of(existingSchedule));
        when(employeeScheduleRepository.findOverlappingSchedulesExcluding(any(Instant.class), any(Instant.class), any(UUID.class)))
                .thenReturn(List.of(conflictingSchedule));

        // When & Then
        assertThrows(InvalidScheduleException.class,
                () -> employeeScheduleService.rescheduleEmployeeSchedule(scheduleId, newStartTime, newEndTime));
    }
}