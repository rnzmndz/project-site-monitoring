package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents details of an employee's assignment within a specific schedule, including role and audit information.")
public class EmployeeScheduleAssignmentResponseDto {

    @Schema(
            description = "Unique identifier of the schedule assignment",
            example = "b5c34a0e-2d11-4c7d-9a8a-80b4b6c1234f"
    )
    private UUID id;

    @Schema(
            description = "Unique identifier of the assigned employee",
            example = "e9f15b8a-732d-4b9d-91f1-bfca41bde923"
    )
    private UUID employeeId;

    @Schema(
            description = "Full name of the assigned employee",
            example = "Juan Dela Cruz"
    )
    private String employeeName;

    @Schema(
            description = "Unique identifier of the associated schedule",
            example = "d4a2233f-59a9-47d8-b721-55dc2fa31a77"
    )
    private UUID scheduleId;

    @Schema(
            description = "Description of the associated schedule",
            example = "Night Shift - Production Line B"
    )
    private String scheduleDescription;

    @Schema(
            description = "Timestamp when the employee was assigned to the schedule (UTC)",
            example = "2025-10-26T08:00:00Z"
    )
    private Instant assignedAt;

    @Schema(
            description = "Role or position of the employee in the assigned schedule",
            example = "Supervisor"
    )
    private String role;

    // Auditing fields
    @Schema(
            description = "Timestamp when the record was created",
            example = "2025-10-26T08:15:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the record was last updated",
            example = "2025-10-27T09:45:00"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "User who created the record",
            example = "system_admin"
    )
    private String createdBy;

    @Schema(
            description = "User who last modified the record",
            example = "hr_manager"
    )
    private String modifiedBy;
}
