package peaksoft.service;

import peaksoft.dto.pagination.SubcategoryPagination;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.SubcategoryResponseByCategory;

import java.util.List;
import java.util.Map;

public interface SubcategoryService {
    SubcategoryPagination getAllSubcategory (int page, int size);

    SimpleResponse save(SubcategoryRequest request);

    SimpleResponse update(Long id, SubcategoryRequest request);

    SimpleResponse delete(Long id);

    List<SubCategoryResponse> sorting (String ascOrDesc);

    Map<String,List<SubcategoryResponseByCategory>> groupingByCategory();
}
