package site.renzoproject.employee_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.employee_service.model.Attendance;

import java.util.UUID;

public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
}