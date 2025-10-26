package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
@Schema(description = "Represents a detailed view of an employee schedule, including assigned employees and audit information")
public class EmployeeScheduleResponseDto {

    @Schema(
            description = "Unique identifier of the schedule",
            example = "4d7f8a31-3c5f-47b8-8a34-1e99e6bcd421"
    )
    private UUID id;

    @Schema(
            description = "Description of the schedule, such as shift details or task summary",
            example = "Morning Shift - Maintenance Team"
    )
    private String description;

    @Schema(
            description = "Start time of the schedule in UTC",
            example = "2025-10-26T00:00:00Z"
    )
    private Instant startTime;

    @Schema(
            description = "End time of the schedule in UTC",
            example = "2025-10-26T08:00:00Z"
    )
    private Instant endTime;

    @Schema(
            description = "Type of schedule (e.g., Regular, Overtime, Holiday)",
            example = "Regular"
    )
    private String scheduleType;

    @Schema(
            description = "Current status of the schedule (e.g., ACTIVE, COMPLETED, CANCELLED)",
            example = "ACTIVE"
    )
    private String status;

    @Schema(
            description = "List of employee assignments related to this schedule"
    )
    private Set<EmployeeScheduleAssignmentResponseDto> assignments = new HashSet<>();

    // Auditing fields
    @Schema(
            description = "Timestamp when the record was created",
            example = "2025-10-25T14:30:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the record was last updated",
            example = "2025-10-26T09:15:00"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "User who created the record",
            example = "scheduler_admin"
    )
    private String createdBy;

    @Schema(
            description = "User who last modified the record",
            example = "supervisor_john"
    )
    private String modifiedBy;
}
