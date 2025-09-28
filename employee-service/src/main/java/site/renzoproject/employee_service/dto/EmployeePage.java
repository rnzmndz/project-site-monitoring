package site.renzoproject.employee_service.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class EmployeePage extends CustomPage<EmployeeResponseDto> {
    public EmployeePage(List<EmployeeResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
