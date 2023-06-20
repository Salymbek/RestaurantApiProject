package peaksoft.dto.pagination;

import lombok.*;
import peaksoft.dto.response.CategoryResponse;

import java.util.List;

@Setter
@Getter
public class CategoryPagination {
    private List<CategoryResponse> categories;
    private int currentPage;
    private int pageSize;
}
