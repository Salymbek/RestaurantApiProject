package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.AllRestaurant;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {


    @Query("select new peaksoft.dto.response.AllRestaurant(r.id,r.name,r.location,r.restType)from Restaurant r")
    List<AllRestaurant> findAllRestaurant();

    @Query("select new peaksoft.dto.response.RestaurantResponse(r.id,r.name,r.location,r.restType,r.service) from Restaurant r  where r.id=:id")
    Optional<RestaurantResponse> findRestaurantById(Long id);
    @Query("select  r from  Restaurant  r")
    Restaurant findRestaurant();

    @Query("select r from Restaurant r")
    Optional<Restaurant> findRestaurants();
}