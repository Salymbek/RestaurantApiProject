package peaksoft.dto.pagination;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;

@Setter
@Getter
@Builder
public class SubcategoryPagination {
    private List<SubCategoryResponse> subcategories;
    private int currentPage;
    private int pageSize;
}
