package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Schema(description = "Paginated response containing attendance records and pagination metadata.")
public class AttendancePage extends CustomPage<AttendanceResponseDto> {

    public AttendancePage(List<AttendanceResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
