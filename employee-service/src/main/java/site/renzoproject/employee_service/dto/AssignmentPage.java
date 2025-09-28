package site.renzoproject.employee_service.dto;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class AssignmentPage extends CustomPage<EmployeeScheduleAssignmentResponseDto> {
    public AssignmentPage(List<EmployeeScheduleAssignmentResponseDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
