package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScheduleAssignmentRequestDto {

    private UUID employeeId;
    private UUID employeeScheduleId;
    private Instant assignedAt;
    private String role;
}
