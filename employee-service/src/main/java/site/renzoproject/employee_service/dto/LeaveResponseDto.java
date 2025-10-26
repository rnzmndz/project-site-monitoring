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
@Schema(description = "Represents a leave record returned by the system")
public class LeaveResponseDto {

    @Schema(description = "Unique identifier of the leave", example = "b21a6f92-ef48-4d8b-9d1b-7b8a23aabf98")
    private UUID id;

    @Schema(description = "Employee's unique identifier", example = "a61b76df-8cd3-4b94-9e1f-d86c234d6c24")
    private UUID employeeId;

    @Schema(description = "Full name of the employee", example = "Juan Dela Cruz")
    private String employeeName;

    @Schema(description = "Type of leave requested", example = "Vacation Leave")
    private String leaveType;

    @Schema(description = "Start date of the leave", example = "2025-04-15")
    private LocalDate startDate;

    @Schema(description = "End date of the leave", example = "2025-04-20")
    private LocalDate endDate;

    @Schema(description = "Current status of the leave request", example = "APPROVED")
    private String status;

    @Schema(description = "Reason for the leave request", example = "Family trip to the province")
    private String reason;

    // Auditing fields
    @Schema(description = "Date and time when the record was created", example = "2025-04-10T08:45:30")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time when the record was last updated", example = "2025-04-12T09:15:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who created the record", example = "admin")
    private String createdBy;

    @Schema(description = "User who last modified the record", example = "hr_manager")
    private String modifiedBy;
}
