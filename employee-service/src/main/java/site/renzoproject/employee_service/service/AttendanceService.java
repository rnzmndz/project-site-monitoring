package site.renzoproject.employee_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.employee_service.dto.AttendanceRequestDto;
import site.renzoproject.employee_service.dto.AttendanceResponseDto;
import site.renzoproject.employee_service.dto.AttendanceSummaryDto;
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
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeScheduleRepository employeeScheduleRepository;
    private final AttendanceMapper attendanceMapper;
    private final EmployeeMapper employeeMapper;

    // CREATE operations
    public AttendanceResponseDto createAttendance(AttendanceRequestDto requestDto) {
        log.info("Creating new attendance record for employee: {}", requestDto.getEmployeeId());

        validateAttendanceRequest(requestDto);

        Employee employee = findEmployeeById(requestDto.getEmployeeId());
        EmployeeSchedule schedule = null;

        if (requestDto.getScheduleId() != null) {
            schedule = findScheduleById(requestDto.getScheduleId());
        }

        Attendance attendance = attendanceMapper.toEntity(requestDto);
        attendance.setId(UUID.randomUUID());
        attendance.setEmployee(employee);
        attendance.setEmployeeSchedule(schedule);

        // Set default source if not provided
        if (attendance.getSource() == null) {
            attendance.setSource("MANUAL");
        }

        Attendance savedAttendance = attendanceRepository.save(attendance);
        log.info("Successfully created attendance record with ID: {}", savedAttendance.getId());

        return attendanceMapper.toResponseDto(savedAttendance, employeeMapper);
    }

    public AttendanceResponseDto checkIn(UUID employeeId, UUID scheduleId, String source) {
        log.info("Processing check-in for employee: {}, schedule: {}", employeeId, scheduleId);

        Employee employee = findEmployeeById(employeeId);
        EmployeeSchedule schedule = scheduleId != null ? findScheduleById(scheduleId) : null;

        // Check if there's an open attendance record (checked in but not checked out)
        Optional<Attendance> existingOpenAttendance = attendanceRepository
                .findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId);

        if (existingOpenAttendance.isPresent()) {
            throw new IllegalStateException("Employee already has an open attendance record. Please check out first.");
        }

        Attendance attendance = Attendance.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .employeeSchedule(schedule)
                .checkIn(Instant.now())
                .status("PRESENT")
                .source(source != null ? source : "BIOMETRIC")
                .build();

        Attendance savedAttendance = attendanceRepository.save(attendance);
        log.info("Check-in recorded successfully for employee: {}", employeeId);

        return attendanceMapper.toResponseDto(savedAttendance, employeeMapper);
    }

    public AttendanceResponseDto checkOut(UUID employeeId, String source) {
        log.info("Processing check-out for employee: {}", employeeId);

        Attendance openAttendance = attendanceRepository
                .findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("No open attendance record found for employee"));

        openAttendance.setCheckOut(Instant.now());
        openAttendance.setSource(source != null ? source : openAttendance.getSource());

        // Update status based on check-in/check-out times
        updateAttendanceStatus(openAttendance);

        Attendance updatedAttendance = attendanceRepository.save(openAttendance);
        log.info("Check-out recorded successfully for employee: {}", employeeId);

        return attendanceMapper.toResponseDto(updatedAttendance, employeeMapper);
    }

    // READ operations
    @Transactional(readOnly = true)
    public AttendanceResponseDto getAttendanceById(UUID id) {
        log.debug("Fetching attendance record with ID: {}", id);

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found with ID: " + id));

        return attendanceMapper.toResponseDto(attendance, employeeMapper);
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAllAttendances() {
        log.debug("Fetching all attendance records");

        List<Attendance> attendances = attendanceRepository.findAll();
        return attendanceMapper.toResponseDtoList(attendances, employeeMapper);
    }

    @Transactional(readOnly = true)
    public Page<AttendanceResponseDto> getAllAttendances(Pageable pageable) {
        log.debug("Fetching attendance records with pagination: {}", pageable);

        Page<Attendance> attendancePage = attendanceRepository.findAll(pageable);
        return attendancePage.map(attendance -> attendanceMapper.toResponseDto(attendance, employeeMapper));
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendancesByEmployee(UUID employeeId) {
        log.debug("Fetching attendance records for employee: {}", employeeId);

        findEmployeeById(employeeId); // Validate employee exists

        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);
        return attendanceMapper.toResponseDtoList(attendances, employeeMapper);
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendancesBySchedule(UUID scheduleId) {
        log.debug("Fetching attendance records for schedule: {}", scheduleId);

        if (scheduleId != null) {
            findScheduleById(scheduleId); // Validate schedule exists
        }

        List<Attendance> attendances = attendanceRepository.findByEmployeeScheduleId(scheduleId);
        return attendanceMapper.toResponseDtoList(attendances, employeeMapper);
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendancesByDateRange(LocalDate startDate, LocalDate endDate) {
        log.debug("Fetching attendance records between {} and {}", startDate, endDate);

        Instant startInstant = startDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<Attendance> attendances = attendanceRepository.findByCheckInBetween(startInstant, endInstant);
        return attendanceMapper.toResponseDtoList(attendances, employeeMapper);
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendancesByEmployeeAndDate(UUID employeeId, LocalDate date) {
        log.debug("Fetching attendance records for employee: {} on date: {}", employeeId, date);

        findEmployeeById(employeeId);

        Instant startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<Attendance> attendances = attendanceRepository.findByEmployeeIdAndCheckInBetween(
                employeeId, startOfDay, endOfDay);
        return attendanceMapper.toResponseDtoList(attendances, employeeMapper);
    }

    @Transactional(readOnly = true)
    public List<AttendanceResponseDto> getAttendancesByStatus(String status) {
        log.debug("Fetching attendance records with status: {}", status);

        List<Attendance> attendances = attendanceRepository.findByStatus(status);
        return attendanceMapper.toResponseDtoList(attendances, employeeMapper);
    }

    @Transactional(readOnly = true)
    public List<AttendanceSummaryDto> getAttendanceSummaryByEmployee(UUID employeeId) {
        log.debug("Fetching attendance summary for employee: {}", employeeId);

        findEmployeeById(employeeId);

        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);
        return attendances.stream()
                .map(attendance -> attendanceMapper.toSummaryDto(attendance, employeeMapper))
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<AttendanceResponseDto> getCurrentAttendance(UUID employeeId) {
        log.debug("Fetching current attendance for employee: {}", employeeId);

        Optional<Attendance> currentAttendance = attendanceRepository
                .findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(employeeId);

        return currentAttendance.map(attendance -> attendanceMapper.toResponseDto(attendance, employeeMapper));
    }

    // UPDATE operations
    public AttendanceResponseDto updateAttendance(UUID id, AttendanceRequestDto requestDto) {
        log.info("Updating attendance record with ID: {}", id);

        Attendance existingAttendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found with ID: " + id));

        validateAttendanceRequest(requestDto);

        // Store original values for logging
        Instant originalCheckIn = existingAttendance.getCheckIn();
        Instant originalCheckOut = existingAttendance.getCheckOut();

        attendanceMapper.updateEntityFromDto(requestDto, existingAttendance);

        // Update employee and schedule references if provided
        if (requestDto.getEmployeeId() != null) {
            Employee employee = findEmployeeById(requestDto.getEmployeeId());
            existingAttendance.setEmployee(employee);
        }

        if (requestDto.getScheduleId() != null) {
            EmployeeSchedule schedule = findScheduleById(requestDto.getScheduleId());
            existingAttendance.setEmployeeSchedule(schedule);
        }

        // Update status based on new times
        updateAttendanceStatus(existingAttendance);

        Attendance updatedAttendance = attendanceRepository.save(existingAttendance);

        log.info("Successfully updated attendance record ID: {}. CheckIn: {}->{}, CheckOut: {}->{}",
                id, originalCheckIn, updatedAttendance.getCheckIn(),
                originalCheckOut, updatedAttendance.getCheckOut());

        return attendanceMapper.toResponseDto(updatedAttendance, employeeMapper);
    }

    public AttendanceResponseDto updateAttendanceStatus(UUID id, String status) {
        log.info("Updating status for attendance record ID: {} to {}", id, status);

        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found with ID: " + id));

        attendance.setStatus(status);
        Attendance updatedAttendance = attendanceRepository.save(attendance);

        log.info("Successfully updated status for attendance record ID: {}", id);
        return attendanceMapper.toResponseDto(updatedAttendance, employeeMapper);
    }

    // DELETE operations
    public void deleteAttendance(UUID id) {
        log.info("Deleting attendance record with ID: {}", id);

        if (!attendanceRepository.existsById(id)) {
            throw new IllegalArgumentException("Attendance record not found with ID: " + id);
        }

        attendanceRepository.deleteById(id);
        log.info("Successfully deleted attendance record with ID: {}", id);
    }

    public void deleteAttendancesByEmployee(UUID employeeId) {
        log.info("Deleting all attendance records for employee: {}", employeeId);

        findEmployeeById(employeeId); // Validate employee exists

        long deletedCount = attendanceRepository.deleteByEmployeeId(employeeId);
        log.info("Successfully deleted {} attendance records for employee: {}", deletedCount, employeeId);
    }

    // BUSINESS LOGIC methods
    private void validateAttendanceRequest(AttendanceRequestDto requestDto) {
        if (requestDto == null) {
            throw new IllegalArgumentException("Attendance request cannot be null");
        }

        if (requestDto.getEmployeeId() == null) {
            throw new IllegalArgumentException("Employee ID is required");
        }

        // Validate that check-out is not before check-in
        if (requestDto.getCheckIn() != null && requestDto.getCheckOut() != null) {
            if (requestDto.getCheckOut().isBefore(requestDto.getCheckIn())) {
                throw new IllegalArgumentException("Check-out time cannot be before check-in time");
            }
        }

        // Validate status if provided
        if (requestDto.getStatus() != null) {
            validateAttendanceStatus(requestDto.getStatus());
        }
    }

    private void validateAttendanceStatus(String status) {
        List<String> validStatuses = List.of("PRESENT", "LATE", "ABSENT", "HALF_DAY", "EARLY_LEAVE");
        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid attendance status: " + status);
        }
    }

    private void updateAttendanceStatus(Attendance attendance) {
        if (attendance.getCheckIn() == null) {
            attendance.setStatus("ABSENT");
            return;
        }

        if (attendance.getCheckOut() == null) {
            // Still checked in, status remains as is or default to PRESENT
            if (attendance.getStatus() == null) {
                attendance.setStatus("PRESENT");
            }
            return;
        }

        // Calculate duration and set status accordingly
        long durationMinutes = java.time.Duration.between(attendance.getCheckIn(), attendance.getCheckOut()).toMinutes();

        if (durationMinutes < 240) { // Less than 4 hours
            attendance.setStatus("HALF_DAY");
        } else if (durationMinutes < 480) { // Less than 8 hours
            attendance.setStatus("EARLY_LEAVE");
        } else {
            attendance.setStatus("PRESENT");
        }
    }

    public AttendanceResponseDto markAsLate(UUID attendanceId, String reason) {
        log.info("Marking attendance ID: {} as LATE", attendanceId);

        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new IllegalArgumentException("Attendance record not found"));

        attendance.setStatus("LATE");
        if (reason != null && attendance.getSource() != null) {
            attendance.setSource(attendance.getSource() + " - " + reason);
        }

        Attendance updatedAttendance = attendanceRepository.save(attendance);
        return attendanceMapper.toResponseDto(updatedAttendance, employeeMapper);
    }

    public AttendanceResponseDto markAsAbsent(UUID employeeId, LocalDate date, String reason) {
        log.info("Marking employee: {} as ABSENT on date: {}", employeeId, date);

        Employee employee = findEmployeeById(employeeId);

        // Check if attendance already exists for this date
        Instant startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<Attendance> existingAttendances = attendanceRepository
                .findByEmployeeIdAndCheckInBetween(employeeId, startOfDay, endOfDay);

        if (!existingAttendances.isEmpty()) {
            throw new IllegalStateException("Attendance record already exists for employee on date: " + date);
        }

        Attendance absence = Attendance.builder()
                .id(UUID.randomUUID())
                .employee(employee)
                .checkIn(null)
                .checkOut(null)
                .status("ABSENT")
                .source("SYSTEM - " + (reason != null ? reason : "Marked absent"))
                .build();

        Attendance savedAbsence = attendanceRepository.save(absence);
        return attendanceMapper.toResponseDto(savedAbsence, employeeMapper);
    }

    // UTILITY methods
    private Employee findEmployeeById(UUID employeeId) {
        return employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));
    }

    private EmployeeSchedule findScheduleById(UUID scheduleId) {
        return employeeScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found with ID: " + scheduleId));
    }

    @Transactional(readOnly = true)
    public long getAttendanceCount() {
        return attendanceRepository.count();
    }

    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        return attendanceRepository.existsById(id);
    }

    public void bulkCreateAttendances(List<AttendanceRequestDto> requestDtos) {
        log.info("Bulk creating {} attendance records", requestDtos.size());

        List<Attendance> attendances = requestDtos.stream()
                .map(dto -> {
                    validateAttendanceRequest(dto);
                    Attendance attendance = attendanceMapper.toEntity(dto);
                    attendance.setId(UUID.randomUUID());

                    Employee employee = findEmployeeById(dto.getEmployeeId());
                    attendance.setEmployee(employee);

                    if (dto.getScheduleId() != null) {
                        EmployeeSchedule schedule = findScheduleById(dto.getScheduleId());
                        attendance.setEmployeeSchedule(schedule);
                    }

                    if (attendance.getSource() == null) {
                        attendance.setSource("BULK_IMPORT");
                    }

                    return attendance;
                })
                .toList();

        List<Attendance> savedAttendances = attendanceRepository.saveAll(attendances);
        log.info("Successfully created {} attendance records in bulk", savedAttendances.size());
    }
}
