package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.StopListResponse;
import peaksoft.model.StopList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.response.StopListResponse(s.id,s.menuItems.name,s.reason,s.date) from StopList s")
    List<StopListResponse> findAllStopList();


//    @Query("select count(*) from StopList s where s.date =:date and upper(s.menuItems.name) like upper(:menuItemName)")
//    int counts(LocalDate date, String menuItemName);


    boolean existsByDateAndMenuItems_Name(LocalDate date, String name);

    boolean existsByMenuItems_NameAndDateAndIdNot(String name, LocalDate date, Long id);


    @Override
    Page<StopList> findAll(Pageable pageable);
}