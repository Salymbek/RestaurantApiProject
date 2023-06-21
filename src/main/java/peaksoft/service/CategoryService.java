package peaksoft.service;

import peaksoft.dto.pagination.CategoryPagination;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CategoryService {
    CategoryPagination getCategory(int page, int size);

    SimpleResponse save(CategoryRequest request);

    SimpleResponse update(Long id,CategoryRequest request);

    SimpleResponse delete(Long id);

}
