package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request payload for assigning an employee to a specific schedule.")
public class EmployeeScheduleAssignmentRequestDto {

    @NotNull(message = "Employee ID is required")
    @Schema(
            description = "Unique identifier of the employee being assigned",
            example = "c7a32f19-1a4e-45b0-b95f-2f60e0e6c723",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID employeeId;

    @Schema(
            description = "Full name of the employee (optional, may be auto-filled by system)",
            example = "Juan Dela Cruz"
    )
    private String employeeName;

    @NotNull(message = "Schedule ID is required")
    @Schema(
            description = "Unique identifier of the schedule to which the employee is assigned",
            example = "a9b712a4-3e22-4f65-871c-7d9cfb8ad892",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID employeeScheduleId;

    @NotNull(message = "Assigned at timestamp is required")
    @Schema(
            description = "Timestamp indicating when the employee was assigned to the schedule (UTC time in ISO-8601 format)",
            example = "2025-10-27T08:00:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Instant assignedAt;

    @NotBlank(message = "Role is required")
    @Schema(
            description = "Role of the employee in the assigned schedule (e.g., Trainer, Trainee, Shift Worker)",
            example = "Trainer",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String role; // Trainer, Trainee, Shift Worker
}
