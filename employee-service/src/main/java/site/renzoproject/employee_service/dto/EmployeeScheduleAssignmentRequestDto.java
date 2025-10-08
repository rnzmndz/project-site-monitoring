package site.renzoproject.employee_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScheduleAssignmentRequestDto {

    @NotNull(message = "Employee ID is required")
    private UUID employeeId;

    private String employeeName;

    @NotNull(message = "Schedule ID is required")
    private UUID employeeScheduleId;

    @NotNull(message = "Assigned at timestamp is required")
    private Instant assignedAt;

    @NotBlank(message = "Role is required")
    private String role; // Trainer, Trainee, Shift Worker
}
