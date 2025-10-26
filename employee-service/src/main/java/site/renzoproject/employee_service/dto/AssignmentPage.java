package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(
        description = "Paginated response containing employee schedule assignment records with pagination metadata."
)
public class AssignmentPage extends CustomPage<EmployeeScheduleAssignmentResponseDto> {

    public AssignmentPage(List<EmployeeScheduleAssignmentResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
