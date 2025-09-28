package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.Attendance;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Optional<Attendance> findFirstByEmployeeIdAndCheckOutIsNullOrderByCheckInDesc(UUID employeeId);

    List<Attendance> findByEmployeeId(UUID employeeId);

    List<Attendance> findByEmployeeScheduleId(UUID scheduleId);

    List<Attendance> findByCheckInBetween(Instant startInstant, Instant endInstant);

    List<Attendance> findByEmployeeIdAndCheckInBetween(UUID employeeId, Instant startOfDay, Instant endOfDay);

    List<Attendance> findByStatus(String status);

    long deleteByEmployeeId(UUID employeeId);
}