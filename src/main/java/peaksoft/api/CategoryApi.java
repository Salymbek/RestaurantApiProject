package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.CategoryResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping
    @PreAuthorize("permitAll()")
    List<CategoryResponse> getAll(){
        return categoryService.findAll();
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

//    @GetMapping("/pagination")
//    public CategoryPagination getCategory(@RequestParam int page,
//                                          @RequestParam int size){
//        return categoryService.getCategoryPagination(page,size);
//    }
}