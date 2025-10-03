package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByAccountId(UUID accountId);

    List<Employee> findByDepartmentContainingIgnoreCase(String department);

    List<Employee> findByJobTitleContainingIgnoreCase(String jobTitle);

    List<Employee> findByHiredDateAfter(LocalDate date);
}