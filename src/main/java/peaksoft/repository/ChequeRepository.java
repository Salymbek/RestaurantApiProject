package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuChequesResponse;
import peaksoft.model.Cheque;

import java.util.List;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new peaksoft.dto.response.ChequeResponse(c.id, c.createdAt,concat(c.user.firstName,' ',c.user.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.user.id = ?1 group by c.id, c.createdAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    List<ChequeResponse> findAllChequeByUserId (Long userId);

    @Query("select new peaksoft.dto.response.MenuChequesResponse(m.id, m.name, m.price, count(m)) from MenuItem m join m.cheques c where c.id = ?1 group by m.id, m.name, m.price")
    List<MenuChequesResponse> getFoods (Long chequeId);


}