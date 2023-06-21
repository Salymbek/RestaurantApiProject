package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.StopListResponse;
import peaksoft.model.StopList;

import java.time.LocalDate;

public interface StopListRepository extends JpaRepository<StopList, Long> {
    @Query("select new peaksoft.dto.response.StopListResponse(s.id,s.menuItems.name,s.reason,s.date) from StopList s")
    Page<StopListResponse> findAllStopList(Pageable pageable);

    boolean existsByDateAndMenuItems_Name(LocalDate date, String name);

    boolean existsByMenuItems_NameAndDateAndIdNot(String name, LocalDate date, Long id);

}