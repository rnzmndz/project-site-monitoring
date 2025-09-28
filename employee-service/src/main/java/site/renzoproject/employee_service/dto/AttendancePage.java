package site.renzoproject.employee_service.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class AttendancePage extends CustomPage<AttendanceResponseDto> {
    public AttendancePage(List<AttendanceResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
