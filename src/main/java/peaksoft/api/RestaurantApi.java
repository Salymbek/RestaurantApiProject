package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.RestaurantService;


@RestController
@RequestMapping("/api/restaurant")
public class RestaurantApi {

    private final RestaurantService restaurantService;

    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest){
        return restaurantService.save(restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return restaurantService.delete(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public RestaurantResponse getById(@PathVariable Long id){
        return restaurantService.getById(id);
    }


    @PutMapping ("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse update(@PathVariable Long id,@RequestBody @Valid RestaurantRequest request){
        return restaurantService.update(id, request);
    }

    @GetMapping("/count")
    @PreAuthorize("permitAll()")
    public String count(){
        return restaurantService.count();
    }

}