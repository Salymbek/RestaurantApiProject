package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.SubCategoryResponse;
import peaksoft.dto.response.SubcategoryResponseByCategory;
import peaksoft.model.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name) from Subcategory s")
    List<SubCategoryResponse> findAllSubCategoryResponse();
    List<SubCategoryResponse> findAllByCategoryIdOrderByName(Long id);

    @Query("select new peaksoft.dto.response.SubcategoryResponseByCategory(c.name,s.name) from Category c join c.subcategories s")
    List<SubcategoryResponseByCategory> findAllGrouping();
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from Subcategory s order by s.name")
    List<SubCategoryResponse> ascSorting();
    @Query("select new peaksoft.dto.response.SubCategoryResponse(s.id,s.name)from Subcategory s  order by s.name desc ")
    List<SubCategoryResponse> descSorting();

    boolean existsByName(String name);

    Optional<Subcategory> findByName(String name);

    @Override
    Page<Subcategory> findAll(Pageable pageable);
}