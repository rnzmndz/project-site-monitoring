package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.EmployeeSchedule;

import java.util.UUID;

public interface EmployeeScheduleRepository extends JpaRepository<EmployeeSchedule, UUID> {
}