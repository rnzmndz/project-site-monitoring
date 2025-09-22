package site.renzoproject.employee_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee_schedule_assignments")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmployeeScheduleAssignment {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_schedule_id", nullable = false)
    private EmployeeSchedule employeeSchedule;

    private Instant assignedAt;

    private String role; // Trainer, Trainee, Shift Worker
}
