package site.renzoproject.employee_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employee_schedules")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class EmployeeSchedule {

    @Id
    private UUID id;

    private String description;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "schedule_type")
    private String scheduleType; // SHIFT, LEAVE, TRAINING, USAGE

    @Column(name = "status")
    private String status; // PLANNED, APPROVED, CANCELLED

    // Join table
    @OneToMany(mappedBy = "employeeSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeScheduleAssignment> assignments = new HashSet<>();

    // Attendance
    @OneToMany(mappedBy = "employeeSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Attendance> attendances = new HashSet<>();

    //Auditing fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;
}
