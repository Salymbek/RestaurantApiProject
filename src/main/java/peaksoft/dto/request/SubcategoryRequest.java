package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SubcategoryRequest(
        @NotEmpty
        String name,
        @NotEmpty(message = " Category name must not be empty!")
        String categoryName
) {
}