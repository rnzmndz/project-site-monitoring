package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScheduleRequestDto {

    private String description;
    private Instant startTime;
    private Instant endTime;
    private String scheduleType;
    private String status;
}
