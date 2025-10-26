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
@Schema(description = "Represents detailed attendance information for an employee, including check-in/out times and audit metadata.")
public class AttendanceResponseDto {

    @Schema(
            description = "Unique identifier of the attendance record.",
            example = "e25a2a2b-4ad8-4bb7-8e7c-9fbc20d3b2b6"
    )
    private UUID id;

    @Schema(
            description = "Unique identifier of the employee.",
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
            description = "Identifier of the employee's assigned schedule (if applicable).",
            example = "a45b5d7e-98b3-41a1-9a1e-8123d4c5b6e7"
    )
    private UUID scheduleId;

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
            description = "Status of the attendance (e.g., Present, Absent, Late).",
            example = "Present"
    )
    private String status;

    @Schema(
            description = "Source of the attendance record (e.g., System, Manual, Biometric).",
            example = "Biometric"
    )
    private String source;

    // Auditing Fields
    @Schema(
            description = "Timestamp when the record was created.",
            example = "2025-10-26T08:10:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the record was last updated.",
            example = "2025-10-26T17:05:00"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "User or system that created this record.",
            example = "system-admin"
    )
    private String createdBy;

    @Schema(
            description = "User or system that last modified this record.",
            example = "attendance-service"
    )
    private String modifiedBy;
}
