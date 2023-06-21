package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.MenuItemAllResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.model.MenuItem;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new peaksoft.dto.response.MenuItemAllResponse(" +
            "m.id,m.subcategory.category.name,m.subcategory.name,m.name,m.price,m.isVegetarian) " +
            "from MenuItem m ")
    List<MenuItemAllResponse> findAllMenuItem();

    @Query("select new peaksoft.dto.response.MenuItemResponse(s.id,s.name,s.image,s.price,s.description,s.isVegetarian)from MenuItem s order by s.price asc ")
    List<MenuItemResponse> ascSorting();
    @Query("select new peaksoft.dto.response.MenuItemResponse(s.id,s.name,s.image,s.price,s.description,s.isVegetarian)from MenuItem s order by s.price desc ")
    List<MenuItemResponse> descSorting();

    @Query("select new peaksoft.dto.response.MenuItemAllResponse(m.id,m.subcategory.category.name,m.subcategory.name,m.name,m.price,m.isVegetarian) from MenuItem m " +
        "where (m.name ilike concat('%',:keyWord, '%') or m.subcategory.name ilike concat('%',:keyWord, '%') or m.subcategory.category.name ilike concat('%',:keyWord, '%')) ")
    List<MenuItemAllResponse> AllSearch(@Param("keyWord") String keyWord);


    List<MenuItemResponse> findMenuItemByIsVegetarian(boolean isTrue);

    boolean existsByName(String name);
}