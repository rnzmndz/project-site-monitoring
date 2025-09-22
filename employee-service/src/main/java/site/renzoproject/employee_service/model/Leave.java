package site.renzoproject.employee_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "leaves")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Leave {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String leaveType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status; // REQUESTED, APPROVED, REJECTED, CANCELLED

    @Column(columnDefinition = "text")
    private String reason;
}
