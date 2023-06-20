package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.pagination.CategoryPagination;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Category;
import peaksoft.repository.CategoryRepository;
import peaksoft.service.CategoryService;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAllCategory();
    }

    @Override
    public SimpleResponse save (CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())){
            throw new AlreadyExistException(String.format(
                    "Is name: %s exists!",categoryRequest.name()));
        }
        Category category = new Category();
        category.setName(categoryRequest.name());

        categoryRepository.save(category);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Category - " + category.getName() + " is saved!")
                .build();
    }

    @Override
    public SimpleResponse update(Long categoryId, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " is not found!"));
        category.setName(categoryRequest.name());
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Category - " + category.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category with id - " + categoryId + " doesn't exists!");
        }
        categoryRepository.deleteById(categoryId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Category with id - " + categoryId + " is deleted")
                .build();
    }

//    @Override
//    public CategoryPagination getCategoryPagination(int page, int size) {
//        Pageable pageable = PageRequest.of(page - 1, size);
//        Page<Category> all = categoryRepository.findAll(pageable);
//
//        CategoryPagination paginationCategoryResponse = new CategoryPagination();
//        paginationCategoryResponse.setCategories(all.getContent());
//        paginationCategoryResponse.setCurrentPage(pageable.getPageNumber()+1);
//        paginationCategoryResponse.setPageSize(all.getTotalPages());
//        return paginationCategoryResponse;
//    }

}