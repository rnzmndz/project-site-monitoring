package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(
        description = "Paginated response containing employee schedule records along with pagination metadata."
)
public class EmployeeSchedulePage extends CustomPage<EmployeeScheduleResponseDto> {

    public EmployeeSchedulePage(List<EmployeeScheduleResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
