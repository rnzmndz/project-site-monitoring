package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.Employee;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}