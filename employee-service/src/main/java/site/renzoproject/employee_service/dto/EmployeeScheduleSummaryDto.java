package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeScheduleSummaryDto {

    private UUID id;
    private String description;
    private Instant startTime;
    private Instant endTime;
    private String scheduleType;
    private String status;
}
