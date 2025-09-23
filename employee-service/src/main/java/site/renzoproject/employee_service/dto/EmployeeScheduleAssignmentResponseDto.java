package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScheduleAssignmentResponseDto {

    private UUID id;
    private UUID employeeId;
    private String EmployeeName;
    private UUID scheduleId;
    private String scheduleDescription;
    private Instant assignedAt;
    private String role;
}
