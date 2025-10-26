package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(
        description = "Represents a paginated response of leave records, including metadata like total elements and current page."
)
public class LeavePage extends CustomPage<LeaveResponseDto> {

    public LeavePage(List<LeaveResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
