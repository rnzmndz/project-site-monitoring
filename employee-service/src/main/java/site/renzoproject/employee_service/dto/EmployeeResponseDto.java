package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents a detailed view of an employee record, including job information and audit metadata.")
public class EmployeeResponseDto {

    @Schema(
            description = "Unique identifier of the employee",
            example = "c8d4c821-6f74-4a12-8f3e-90a7f5c2d9a2"
    )
    private UUID id;

    @Schema(
            description = "Associated account identifier of the employee",
            example = "a4f2e68b-0c19-4a87-9171-1b0f75e4b8ab"
    )
    private UUID accountId;

    @Schema(
            description = "First name of the employee",
            example = "Juan"
    )
    private String firstName;

    @Schema(
            description = "Middle name of the employee (optional)",
            example = "Reyes"
    )
    private String middleName;

    @Schema(
            description = "Last name of the employee",
            example = "Dela Cruz"
    )
    private String lastName;

    @Schema(
            description = "Suffix of the employee’s name (e.g., Jr., Sr., III)",
            example = "Jr."
    )
    private String nameSuffix;

    @Schema(
            description = "Job title or position of the employee",
            example = "Fire Protection Engineer"
    )
    private String jobTitle;

    @Schema(
            description = "Department or division where the employee works",
            example = "Engineering"
    )
    private String department;

    @Schema(
            description = "Date when the employee was hired",
            example = "2023-05-15"
    )
    private LocalDate hiredDate;

    // Auditing fields
    @Schema(
            description = "Timestamp when the record was created",
            example = "2025-10-26T09:00:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Timestamp when the record was last updated",
            example = "2025-10-27T10:45:00"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Username or identifier of the user who created this record",
            example = "admin"
    )
    private String createdBy;

    @Schema(
            description = "Username or identifier of the user who last modified this record",
            example = "hr_manager"
    )
    private String modifiedBy;
}
