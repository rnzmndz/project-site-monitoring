package site.renzoproject.employee_service.exception;

public class EmployeeScheduleNotFoundException extends RuntimeException {
    public EmployeeScheduleNotFoundException(String message) {
        super(message);
    }
}