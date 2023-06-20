package peaksoft.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.SubCategoryResponse;

import java.util.List;

@Setter
@Getter
public class SubcategoryPagination {
    private List<SubCategoryResponse> subcategories;
    private int currentPage;
    private int pageSize;
}
