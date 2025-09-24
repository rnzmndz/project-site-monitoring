package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttendanceResponseDto {

    private UUID id;
    private UUID employeeId;
    private String employeeName;
    private UUID scheduleId;
    private Instant checkIn;
    private Instant checkOut;
    private String status;
    private String source;

    //Auditing fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String modifiedBy;
}
