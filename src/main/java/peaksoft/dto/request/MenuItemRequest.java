package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemRequest(
        @NotEmpty
        String name,
        String image,
        BigDecimal price,
        @NotEmpty
        String description,
        Boolean isVegetarian,
        String subcategory
) {
}
