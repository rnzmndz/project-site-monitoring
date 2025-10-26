package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating an employee schedule")
public class EmployeeScheduleRequestDto {

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Schema(
            description = "Detailed description of the schedule, such as shift purpose or task summary",
            example = "Morning Shift - Assembly Line A",
            maxLength = 500,
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @NotNull(message = "Start time is required")
    @Schema(
            description = "Start time of the schedule in UTC",
            example = "2025-10-27T00:00:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Instant startTime;

    @NotNull(message = "End time is required")
    @Schema(
            description = "End time of the schedule in UTC",
            example = "2025-10-27T08:00:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Instant endTime;

    @Size(max = 50, message = "Schedule type must not exceed 50 characters")
    @Schema(
            description = "Type of schedule (e.g., Regular, Overtime, Holiday)",
            example = "Regular",
            maxLength = 50
    )
    private String scheduleType;

    @Size(max = 50, message = "Status must not exceed 50 characters")
    @Schema(
            description = "Current status of the schedule (e.g., ACTIVE, PENDING, CANCELLED)",
            example = "ACTIVE",
            maxLength = 50
    )
    private String status;
}
