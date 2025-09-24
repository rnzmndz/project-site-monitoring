package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceSummaryDto {

    private UUID id;
    private UUID employeeId;
    private String employeeName;
    private Instant checkIn;
    private Instant checkOut;
    private String status;
}
