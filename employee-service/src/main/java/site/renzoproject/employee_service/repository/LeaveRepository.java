package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.Leave;

import java.util.UUID;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {
}