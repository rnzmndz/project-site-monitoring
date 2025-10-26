package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(
        description = "Paginated response containing employee records along with pagination metadata."
)
public class EmployeePage extends CustomPage<EmployeeResponseDto> {

    public EmployeePage(List<EmployeeResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}