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
@Schema(description = "Request payload for recording or updating an employee's attendance.")
public class AttendanceRequestDto {

    @Schema(
            description = "Unique identifier of the employee associated with this attendance record.",
            example = "a1b2c3d4-5678-90ef-1234-56789abcdef0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private UUID employeeId;

    @Schema(
            description = "Full name of the employee. This field is optional and may be auto-populated.",
            example = "Juan Dela Cruz"
    )
    private String employeeName;

    @Schema(
            description = "Unique identifier of the associated schedule (if applicable).",
            example = "b1234567-89ab-cdef-0123-456789abcdef"
    )
    private UUID scheduleId;

    @Schema(
            description = "Timestamp indicating when the employee checked in (ISO 8601 format).",
            example = "2025-10-26T08:00:00Z",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Instant checkIn;

    @Schema(
            description = "Timestamp indicating when the employee checked out (ISO 8601 format).",
            example = "2025-10-26T17:00:00Z"
    )
    private Instant checkOut;

    @Schema(
            description = "Status of the attendance (e.g., Present, Absent, Late).",
            example = "Present",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String status;

    @Schema(
            description = "Source of the attendance entry (e.g., System, Manual, Biometric).",
            example = "Biometric"
    )
    private String source;
}