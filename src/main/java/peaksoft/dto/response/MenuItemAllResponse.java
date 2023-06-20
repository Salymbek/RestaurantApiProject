package peaksoft.dto.response;

import java.math.BigDecimal;

public record MenuItemAllResponse(
        Long id,
        String categoryName,
        String subCategoryName,
        String name,
        BigDecimal price,
        Boolean isVegetarian

) {
}