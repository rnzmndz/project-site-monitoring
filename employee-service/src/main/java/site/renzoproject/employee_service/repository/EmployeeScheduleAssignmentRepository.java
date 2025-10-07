package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;

import java.util.List;
import java.util.UUID;

public interface EmployeeScheduleAssignmentRepository extends JpaRepository<EmployeeScheduleAssignment, UUID> {

    @Query("SELECT esa FROM EmployeeScheduleAssignment esa WHERE esa.employee.id = :employeeId")
    List<EmployeeScheduleAssignment> findByEmployeeId(@Param("employeeId") UUID employeeId);

    @Query("SELECT esa FROM EmployeeScheduleAssignment esa WHERE esa.employeeSchedule.id = :scheduleId")
    List<EmployeeScheduleAssignment> findByScheduleId(@Param("scheduleId") UUID scheduleId);

    @Query("SELECT COUNT(esa) > 0 FROM EmployeeScheduleAssignment esa WHERE esa.employee.id = :employeeId AND esa.employeeSchedule.id = :scheduleId")
    boolean existsByEmployeeIdAndScheduleId(@Param("employeeId") UUID employeeId, @Param("scheduleId") UUID scheduleId);

    @Query("SELECT esa FROM EmployeeScheduleAssignment esa WHERE esa.role = :role")
    List<EmployeeScheduleAssignment> findByRole(@Param("role") String role);
}