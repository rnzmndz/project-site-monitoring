package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@Schema(description = "Standard structure for API error responses")
public class ErrorResponse {

    @Schema(
            description = "Timestamp when the error occurred",
            example = "2025-10-26T14:22:15"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "HTTP status code returned by the server",
            example = "404"
    )
    private int status;

    @Schema(
            description = "Short description of the HTTP error",
            example = "Not Found"
    )
    private String error;

    @Schema(
            description = "Detailed message explaining the error",
            example = "Employee with ID 123e4567-e89b-12d3-a456-426614174000 not found"
    )
    private String message;

    @Schema(
            description = "Additional validation or field-specific error details, if any",
            example = "{\"employeeId\": \"must not be null\"}"
    )
    private Map<String, String> details;

    @Schema(
            description = "Request path where the error occurred",
            example = "/api/v1/leaves/employee/123e4567-e89b-12d3-a456-426614174000"
    )
    private String path;
}