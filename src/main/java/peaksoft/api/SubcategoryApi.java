package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.pagination.SubcategoryPagination;
import peaksoft.dto.request.SubcategoryRequest;
import peaksoft.dto.response.SubcategoryResponseByCategory;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.service.SubcategoryService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subcategory")
public class SubcategoryApi {

    private final SubcategoryService subCategoryService;


    public SubcategoryApi(SubcategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse save(@RequestBody @Valid SubcategoryRequest request){
        return subCategoryService.save(request);
    }

    @PreAuthorize("permitAll()")
    @GetMapping
    public SubcategoryPagination findAll(@RequestParam int page,@RequestParam int size) {
        return subCategoryService.getAllSubcategory(page, size);
    }

    @PutMapping("/{subCategoryId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse update(@PathVariable Long subCategoryId,
                          @RequestBody @Valid SubcategoryRequest request){
        return subCategoryService.update(subCategoryId, request);
    }

    @DeleteMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse delete(@PathVariable Long subCategoryId){
        return subCategoryService.delete(subCategoryId);
    }

    @GetMapping("/sort")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public List<SubCategoryResponse>sortSubcategory(@RequestParam(required = false) String ascOrDesc){
        return subCategoryService.sorting(ascOrDesc);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/groupBy")
    public Map<String, List<SubcategoryResponseByCategory>> grouping() {
        return subCategoryService.groupingByCategory();
    }


}