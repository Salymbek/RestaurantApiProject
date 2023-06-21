package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.pagination.CategoryPagination;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    @PreAuthorize("permitAll()")
    public CategoryPagination getAllCategory(@RequestParam int page, @RequestParam int size){
        return categoryService.getCategory(page, size);
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest);
    }
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse update(@PathVariable  Long categoryId,
                          @RequestBody @Valid CategoryRequest categoryRequest){
        return categoryService.update(categoryId, categoryRequest);
    }
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long categoryId){
        return categoryService.delete(categoryId);
    }

}