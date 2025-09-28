package site.renzoproject.employee_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.mapper.AttendanceMapper;
import site.renzoproject.employee_service.mapper.EmployeeMapper;
import site.renzoproject.employee_service.model.Attendance;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.repository.AttendanceRepository;
import site.renzoproject.employee_service.repository.EmployeeRepository;
import site.renzoproject.employee_service.repository.EmployeeScheduleRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private AttendanceService attendanceService;

    private UUID employeeId;
    private UUID scheduleId;
    private UUID attendanceId;
    private Employee employee;
    private EmployeeSchedule schedule;
    private Attendance attendance;
    private AttendanceRequestDto requestDto;
    private AttendanceResponseDto responseDto;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        scheduleId = UUID.randomUUID();
        attendanceId = UUID.randomUUID();

        employee = Employee.builder()
                .id(employeeId)
                .firstName("John")
                .lastName("Doe")
                .build();

        schedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .build();

        attendance = Attendance.builder()
                .id(attendanceId)
                .employee(employee)
                .employeeSchedule(schedule)
                .checkIn(Instant.now().minusSeconds(3600)) // 1 hour ago
                .checkOut(Instant.now())
                .status("PRESENT")
                .source("MANUAL")
                .build();

        requestDto = AttendanceRequestDto.builder()
                .employeeId(employeeId)
                .scheduleId(scheduleId)
                .checkIn(Instant.now().minusSeconds(3600))
                .checkOut(Instant.now())
                .status("PRESENT")
                .build();

        responseDto = AttendanceResponseDto.builder()
                .id(attendanceId)
                .employeeId(employeeId)
                .scheduleId(scheduleId)
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status("PRESENT")
                .source("MANUAL")
                .build();
    }

    @Test
    void createAttendance_ShouldCreateSuccessfully() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(attendanceMapper.toEntity(requestDto)).thenReturn(attendance);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.createAttendance(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(attendanceId, result.getId());
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void createAttendance_WithNullSource_ShouldSetDefaultSource() {
        // Arrange
        requestDto.setSource(null);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(attendanceMapper.toEntity(requestDto)).thenReturn(attendance);
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        attendanceService.createAttendance(requestDto);

        // Assert
        verify(attendanceRepository).save(argThat(att -> "MANUAL".equals(att.getSource())));
    }

    @Test
    void createAttendance_WithInvalidEmployee_ShouldThrowException() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.createAttendance(requestDto));
    }

    @Test
    void checkIn_ShouldCreateNewAttendance() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId))
                .thenReturn(Optional.empty());
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.checkIn(employeeId, scheduleId, "BIOMETRIC");

        // Assert
        assertNotNull(result);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void checkIn_WithExistingOpenAttendance_ShouldThrowException() {
        // Arrange
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        lenient().when(attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId))
                .thenReturn(Optional.of(attendance));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.checkIn(employeeId, scheduleId, "BIOMETRIC"));
    }

    @Test
    void checkOut_ShouldUpdateExistingAttendance() {
        // Arrange
        Attendance openAttendance = Attendance.builder()
                .id(attendanceId)
                .employee(employee)
                .checkIn(Instant.now().minusSeconds(7200))
                .checkOut(null)
                .status("PRESENT")
                .build();

        when(attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId))
                .thenReturn(Optional.of(openAttendance));
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(openAttendance);
        when(attendanceMapper.toResponseDto(openAttendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.checkOut(employeeId, "BIOMETRIC");

        // Assert
        assertNotNull(result);
        assertNotNull(openAttendance.getCheckOut());
        verify(attendanceRepository).save(openAttendance);
    }

    @Test
    void checkOut_WithNoOpenAttendance_ShouldThrowException() {
        // Arrange
        when(attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.checkOut(employeeId, "BIOMETRIC"));
    }

    @Test
    void getAttendanceById_ShouldReturnAttendance() {
        // Arrange
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.getAttendanceById(attendanceId);

        // Assert
        assertNotNull(result);
        assertEquals(attendanceId, result.getId());
    }

    @Test
    void getAttendanceById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.getAttendanceById(attendanceId));
    }

    @Test
    void getAllAttendances_ShouldReturnList() {
        // Arrange
        List<Attendance> attendances = List.of(attendance);
        when(attendanceRepository.findAll()).thenReturn(attendances);
        when(attendanceMapper.toResponseDtoList(attendances, employeeMapper))
                .thenReturn(List.of(responseDto));

        // Act
        List<AttendanceResponseDto> result = attendanceService.getAllAttendances();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAllAttendances_WithPagination_ShouldReturnPage() {
        // Arrange
        Pageable pageable = Pageable.ofSize(10);
        Page<Attendance> attendancePage = new PageImpl<>(List.of(attendance));
        when(attendanceRepository.findAll(pageable)).thenReturn(attendancePage);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        Page<AttendanceResponseDto> result = attendanceService.getAllAttendances(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getAttendancesByEmployee_ShouldReturnEmployeeAttendances() {
        // Arrange
        List<Attendance> attendances = List.of(attendance);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeId(employeeId)).thenReturn(attendances);
        when(attendanceMapper.toResponseDtoList(attendances, employeeMapper))
                .thenReturn(List.of(responseDto));

        // Act
        List<AttendanceResponseDto> result = attendanceService.getAttendancesByEmployee(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getAttendancesByDateRange_ShouldReturnFilteredAttendances() {
        // Arrange
        LocalDate startDate = LocalDate.now().minusDays(7);
        LocalDate endDate = LocalDate.now();
        List<Attendance> attendances = List.of(attendance);

        when(attendanceRepository.findByCheckInBetween(any(Instant.class), any(Instant.class)))
                .thenReturn(attendances);
        when(attendanceMapper.toResponseDtoList(attendances, employeeMapper))
                .thenReturn(List.of(responseDto));

        // Act
        List<AttendanceResponseDto> result = attendanceService.getAttendancesByDateRange(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void updateAttendance_ShouldUpdateSuccessfully() {
        // Arrange
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(attendanceRepository.save(attendance)).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.updateAttendance(attendanceId, requestDto);

        // Assert
        assertNotNull(result);
        verify(attendanceRepository).save(attendance);
    }

    @Test
    void updateAttendanceStatus_ShouldUpdateStatus() {
        // Arrange
        String newStatus = "LATE";
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(attendanceRepository.save(attendance)).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.updateAttendanceStatus(attendanceId, newStatus);

        // Assert
        assertNotNull(result);
        assertEquals(newStatus, attendance.getStatus());
        verify(attendanceRepository).save(attendance);
    }

    @Test
    void deleteAttendance_ShouldDeleteSuccessfully() {
        // Arrange
        when(attendanceRepository.existsById(attendanceId)).thenReturn(true);

        // Act
        attendanceService.deleteAttendance(attendanceId);

        // Assert
        verify(attendanceRepository).deleteById(attendanceId);
    }

    @Test
    void deleteAttendance_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(attendanceRepository.existsById(attendanceId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.deleteAttendance(attendanceId));
    }

    @Test
    void markAsLate_ShouldUpdateStatusToLate() {
        // Arrange
        String reason = "Traffic jam";
        when(attendanceRepository.findById(attendanceId)).thenReturn(Optional.of(attendance));
        when(attendanceRepository.save(attendance)).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.markAsLate(attendanceId, reason);

        // Assert
        assertNotNull(result);
        assertEquals("LATE", attendance.getStatus());
        verify(attendanceRepository).save(attendance);
    }

    @Test
    void markAsAbsent_ShouldCreateAbsenceRecord() {
        // Arrange
        LocalDate date = LocalDate.now();
        String reason = "Sick leave";

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeIdAndCheckInBetween(any(UUID.class), any(Instant.class), any(Instant.class)))
                .thenReturn(Collections.emptyList());
        when(attendanceRepository.save(any(Attendance.class))).thenReturn(attendance);
        when(attendanceMapper.toResponseDto(any(Attendance.class), eq(employeeMapper))).thenReturn(responseDto);

        // Act
        AttendanceResponseDto result = attendanceService.markAsAbsent(employeeId, date, reason);

        // Assert
        assertNotNull(result);
        verify(attendanceRepository).save(any(Attendance.class));
    }

    @Test
    void markAsAbsent_WithExistingAttendance_ShouldThrowException() {
        // Arrange
        LocalDate date = LocalDate.now();
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(attendanceRepository.findByEmployeeIdAndCheckInBetween(any(UUID.class), any(Instant.class), any(Instant.class)))
                .thenReturn(List.of(attendance));

        // Act & Assert
        assertThrows(IllegalStateException.class,
                () -> attendanceService.markAsAbsent(employeeId, date, "Sick"));
    }

    @Test
    void getCurrentAttendance_ShouldReturnOpenAttendance() {
        // Arrange
        when(attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId))
                .thenReturn(Optional.of(attendance));
        when(attendanceMapper.toResponseDto(attendance, employeeMapper)).thenReturn(responseDto);

        // Act
        Optional<AttendanceResponseDto> result = attendanceService.getCurrentAttendance(employeeId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(attendanceId, result.get().getId());
    }

    @Test
    void getAttendanceCount_ShouldReturnCount() {
        // Arrange
        long expectedCount = 5L;
        when(attendanceRepository.count()).thenReturn(expectedCount);

        // Act
        long result = attendanceService.getAttendanceCount();

        // Assert
        assertEquals(expectedCount, result);
    }

    @Test
    void existsById_ShouldReturnTrue() {
        // Arrange
        when(attendanceRepository.existsById(attendanceId)).thenReturn(true);

        // Act
        boolean result = attendanceService.existsById(attendanceId);

        // Assert
        assertTrue(result);
    }

    @Test
    void bulkCreateAttendances_ShouldCreateMultipleRecords() {
        // Arrange
        List<AttendanceRequestDto> requestDtos = List.of(requestDto, requestDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeScheduleRepository.findById(scheduleId)).thenReturn(Optional.of(schedule));
        when(attendanceMapper.toEntity(requestDto)).thenReturn(attendance);
        when(attendanceRepository.saveAll(anyList())).thenReturn(List.of(attendance, attendance));

        // Act
        attendanceService.bulkCreateAttendances(requestDtos);

        // Assert
        verify(attendanceRepository).saveAll(anyList());
    }

    @Test
    void validateAttendanceRequest_WithCheckOutBeforeCheckIn_ShouldThrowException() {
        // Arrange
        requestDto.setCheckIn(Instant.now());
        requestDto.setCheckOut(Instant.now().minusSeconds(3600));

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> attendanceService.createAttendance(requestDto));
    }

    @Test
    void updateAttendanceStatus_ShouldSetCorrectStatusBasedOnDuration() {
        // Arrange
        Attendance testAttendance = Attendance.builder()
                .id(attendanceId)
                .employee(employee)
                .checkIn(Instant.now().minusSeconds(18000)) // 5 hours ago
                .checkOut(Instant.now())
                .build();

        // Act - This would be called internally during checkOut or update operations
        // For testing purposes, we'll call it directly via reflection or test the public methods that use it
        when(attendanceRepository.findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId))
                .thenReturn(Optional.of(testAttendance));
        when(attendanceRepository.save(testAttendance)).thenReturn(testAttendance);
        when(attendanceMapper.toResponseDto(testAttendance, employeeMapper)).thenReturn(responseDto);

        attendanceService.checkOut(employeeId, "TEST");

        // Assert - The status should be set based on duration
        // Since duration is 5 hours (300 minutes), it should be "EARLY_LEAVE"
        assertEquals("EARLY_LEAVE", testAttendance.getStatus());
    }
}