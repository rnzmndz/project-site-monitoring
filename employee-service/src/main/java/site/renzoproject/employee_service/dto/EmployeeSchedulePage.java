package site.renzoproject.employee_service.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class EmployeeSchedulePage extends CustomPage<EmployeeScheduleResponseDto> {
    public EmployeeSchedulePage(List<EmployeeScheduleResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
