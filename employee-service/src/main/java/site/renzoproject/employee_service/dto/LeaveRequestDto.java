package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Represents the request payload for submitting or updating a leave request")
public class LeaveRequestDto {

    @Schema(description = "Unique identifier of the employee submitting the leave",
            example = "c9a1234f-4b12-45d9-9b19-54a6ef31f9b3")
    private UUID employeeId;

    @Schema(description = "Full name of the employee",
            example = "Juan Dela Cruz")
    private String employeeName;

    @Schema(description = "Type of leave being requested (e.g., Vacation, Sick, Emergency)",
            example = "Vacation Leave")
    private String leaveType;

    @Schema(description = "Start date of the requested leave period",
            example = "2025-04-15")
    private LocalDate startDate;

    @Schema(description = "End date of the requested leave period",
            example = "2025-04-20")
    private LocalDate endDate;

    @Schema(description = "Current status of the leave request",
            example = "PENDING")
    private String status;

    @Schema(description = "Reason for requesting the leave",
            example = "Attending a family event out of town")
    private String reason;
}
