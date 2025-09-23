package site.renzoproject.employee_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {

    private UUID accountId;
    private String jobTitle;
    private String department;
    private LocalDate hiredDate;
}
