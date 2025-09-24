package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScheduleAssignmentResponseDto {

    private UUID id;
    private UUID employeeId;
    private String employeeName;
    private UUID scheduleId;
    private String scheduleDescription;
    private Instant assignedAt;
    private String role;

    //Auditing fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String modifiedBy;
}
