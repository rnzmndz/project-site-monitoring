package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;

import java.util.UUID;

public interface EmployeeScheduleAssignmentRepository extends JpaRepository<EmployeeScheduleAssignment, UUID> {
}