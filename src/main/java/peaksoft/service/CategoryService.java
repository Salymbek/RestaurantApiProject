package peaksoft.service;

import peaksoft.dto.pagination.CategoryPagination;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();
    SimpleResponse save(CategoryRequest request);

    SimpleResponse update(Long id,CategoryRequest request);

    SimpleResponse delete(Long id);

  //  CategoryPagination getCategoryPagination(int page, int size);

}
