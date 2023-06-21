package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.pagination.SubcategoryPagination;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.SubcategoryResponseByCategory;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Subcategory;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.SubcategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public SubcategoryPagination getAllSubcategory(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        Page<SubCategoryResponse> allCategory = subcategoryRepository.getAllSubcategory(pageable);
        return SubcategoryPagination.builder()
                .subcategories(allCategory.getContent())
                .currentPage(allCategory.getNumber()+1)
                .pageSize(allCategory.getTotalPages())
                .build();
    }



    @Override
    public SimpleResponse save(SubcategoryRequest request) {
        Subcategory subcategory = new Subcategory();
        if (subcategoryRepository.existsByName(request.name())) {
            throw new AlreadyExistException("Subcategory name already exist!!");
        }
        subcategory.setName(request.name());
        subcategory.setCategory(categoryRepository.findByName(request.categoryName()).orElseThrow(() -> {
            throw new NotFoundException("Category with name - " + request.categoryName() + " is not found!");
        }));

        subcategoryRepository.save(subcategory);
        return new SimpleResponse(HttpStatus.OK, "Successfully saved!");
    }

    @Override
    public SimpleResponse update(Long id, SubcategoryRequest request) {
        Subcategory subcategory = subcategoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Sub category with id - " + id + " is not found!");
        });

        subcategory.setName(request.name());
        subcategory.setCategory(categoryRepository.findByName(request.categoryName()).orElseThrow(() -> {
            throw new NotFoundException("Category with name - " + request.categoryName() + " is not found!");
        }));
        subcategoryRepository.save(subcategory);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Sub category - " + subcategory.getName() + " is updated!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            throw new NotFoundException("Sub category with id - " + id + " doesn't exists!");
        }
        subcategoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Sub category with id - " + id + " is deleted!")
                .build();
    }

    @Override
    public List<SubCategoryResponse> sorting(String ascOrDesc) {
        if (ascOrDesc.equals("desc")){
            return subcategoryRepository.descSorting();
        } else {
            return subcategoryRepository.ascSorting();
        }
    }

    @Override
    public Map<String,List<SubcategoryResponseByCategory>> groupingByCategory() {
        List<SubcategoryResponseByCategory> grouping = subcategoryRepository.findAllGrouping();
        return grouping.stream().collect(Collectors.groupingBy(SubcategoryResponseByCategory::category));
    }


}