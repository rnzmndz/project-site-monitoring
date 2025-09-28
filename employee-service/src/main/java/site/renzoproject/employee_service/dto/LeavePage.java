package site.renzoproject.employee_service.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class LeavePage extends CustomPage<LeaveResponseDto> {
    public LeavePage(List<LeaveResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
