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
@Schema(description = "Represents a summary of an employee's attendance record.")
public class AttendanceSummaryDto {

    @Schema(
            description = "Unique identifier of the attendance record.",
            example = "8e1a13f2-2b6b-4f58-84b5-0cf6acb27c43"
    )
    private UUID id;

    @Schema(
            description = "Unique identifier of the employee associated with this attendance record.",
            example = "a1b2c3d4-5678-90ef-1234-56789abcdef0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID employeeId;

    @Schema(
            description = "Full name of the employee.",
            example = "Juan Dela Cruz"
    )
    private String employeeName;

    @Schema(
            description = "Timestamp indicating when the employee checked in (ISO 8601 format).",
            example = "2025-10-26T08:00:00Z"
    )
    private Instant checkIn;

    @Schema(
            description = "Timestamp indicating when the employee checked out (ISO 8601 format).",
            example = "2025-10-26T17:00:00Z"
    )
    private Instant checkOut;

    @Schema(
            description = "Current status of the attendance record (e.g., Present, Absent, Late).",
            example = "Present"
    )
    private String status;
}

