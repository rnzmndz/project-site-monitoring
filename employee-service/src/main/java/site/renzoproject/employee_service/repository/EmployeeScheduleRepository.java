package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.renzoproject.employee_service.model.EmployeeSchedule;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, UUID> {
    List<EmployeeSchedule> findByScheduleType(String scheduleType);

    List<EmployeeSchedule> findByStatus(String status);

    List<EmployeeSchedule> findByStatusNot(String status);

    List<EmployeeSchedule> findByStartTimeBetween(Instant startTime, Instant endTime);

    List<EmployeeSchedule> findByStartTimeBetweenAndStatusNot(Instant startTime, Instant endTime, String status);

    @Query("SELECT es FROM EmployeeSchedule es WHERE " +
            "(es.startTime <= :endTime AND es.endTime >= :startTime) " +
            "AND es.status != 'CANCELLED'")
    List<EmployeeSchedule> findOverlappingSchedules(@Param("startTime") Instant startTime,
                                                    @Param("endTime") Instant endTime);

    @Query("SELECT es FROM EmployeeSchedule es WHERE " +
            "(es.startTime <= :endTime AND es.endTime >= :startTime) " +
            "AND es.id != :excludeId " +
            "AND es.status != 'CANCELLED'")
    List<EmployeeSchedule> findOverlappingSchedulesExcluding(@Param("startTime") Instant startTime,
                                                             @Param("endTime") Instant endTime,
                                                             @Param("excludeId") UUID excludeId);

    long deleteByStatus(String status);

    long countByStatus(String status);

    long countByScheduleType(String scheduleType);
}