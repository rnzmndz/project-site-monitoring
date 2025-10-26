package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Generic pagination model for API responses.")
public class CustomPage<T> {

    @Schema(
            description = "List of items in the current page.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private List<T> content;

    @Schema(
            description = "Pagination details such as page number, size, and sorting order."
    )
    private Pageable pageable;

    @Schema(
            description = "Total number of items available across all pages.",
            example = "125",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private long total;
}