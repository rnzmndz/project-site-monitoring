package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceRequestDto {

    private UUID employeeId;
    private UUID scheduleId;
    private Instant checkIn;
    private Instant checkOut;
    private String status;
    private String source;
}
