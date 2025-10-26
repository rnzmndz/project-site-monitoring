package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request payload for creating or updating an employee record.")
public class EmployeeRequestDto {

    @Schema(
            description = "Unique identifier of the account associated with the employee.",
            example = "a4f2e68b-0c19-4a87-9171-1b0f75e4b8ab"
    )
    private UUID accountId;

    @Schema(
            description = "First name of the employee.",
            example = "Juan",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String firstName;

    @Schema(
            description = "Middle name of the employee (optional).",
            example = "Reyes"
    )
    private String middleName;

    @Schema(
            description = "Last name of the employee.",
            example = "Dela Cruz",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String lastName;

    @Schema(
            description = "Suffix of the employee’s name (e.g., Jr., Sr., III).",
            example = "Jr."
    )
    private String nameSuffix;

    @Schema(
            description = "Job title or position of the employee.",
            example = "Fire Protection Engineer",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String jobTitle;

    @Schema(
            description = "Department or division where the employee works.",
            example = "Engineering",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String department;

    @Schema(
            description = "Date when the employee was hired.",
            example = "2023-05-15",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private LocalDate hiredDate;
}