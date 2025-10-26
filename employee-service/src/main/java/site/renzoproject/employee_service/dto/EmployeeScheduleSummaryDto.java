package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents a summary of an employee's work schedule")
public class EmployeeScheduleSummaryDto {

    @Schema(
            description = "Unique identifier of the schedule",
            example = "9a7f3f12-8b4c-43d2-bf78-abc123def456"
    )
    private UUID id;

    @Schema(
            description = "Description of the schedule, such as shift details or task summary",
            example = "Morning Shift - Front Desk Operations"
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
}
