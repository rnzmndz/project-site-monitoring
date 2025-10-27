package site.renzoproject.employee_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Schema(description = "Generic paginated response wrapper")
public class CustomPage<T> extends PageImpl<T> {

    public CustomPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CustomPage(List<T> content) {
        super(content);
    }

    @Override
    @Schema(description = "Page content (list of data items)")
    public List<T> getContent() {
        return super.getContent();
    }

    @Override
    @Schema(description = "Current page number (0-based)")
    public int getNumber() {
        return super.getNumber();
    }

    @Override
    @Schema(description = "Number of elements per page")
    public int getSize() {
        return super.getSize();
    }

    @Override
    @Schema(description = "Total number of elements across all pages")
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @Override
    @Schema(description = "Total number of pages")
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @Override
    @Schema(description = "Is this the first page?")
    public boolean isFirst() {
        return super.isFirst();
    }

    @Override
    @Schema(description = "Is this the last page?")
    public boolean isLast() {
        return super.isLast();
    }
}