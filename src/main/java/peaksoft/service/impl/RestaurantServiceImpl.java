package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.UserResponse;
import peaksoft.enums.Role;
import peaksoft.exception.BadCredentialException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Restaurant;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;
import peaksoft.service.UserService;

import java.util.List;

@Service
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository repository;
    private final UserService userService;

    public RestaurantServiceImpl(RestaurantRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }


    @Override
    public RestaurantResponse getById(Long id) {
        return repository.findRestaurantById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Restaurant with id: %s not found!", id)));    }


    @Override
    public SimpleResponse save(RestaurantRequest request) {

        List<Restaurant> restaurants = repository.findAll();
        if (restaurants.size() > 0){
            throw new BadCredentialException("Only one restaurant saved only!!!");
        }
        if (request.service()>50){
            throw new BadCredentialException("Service cannot exceed 50%");
        }
        int size = 0;

        Restaurant restaurant = Restaurant.builder()
                .name(request.name())
                .location(request.location())
                .restType(request.restType())
                .service(request.service())
                .numberOfEmployee(++size)
                .build();

        repository.save(restaurant);



        return SimpleResponse.builder().status(HttpStatus.OK)
                .message(String.format("Restaurant with name :%s successfully saved!",restaurant.getName()))
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        if (!repository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("Restaurant with id: %d is not found", id)).build();
        }

        repository.deleteById(id);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: %d is successfully deleted.", id))
                .build();
    }

    @Override
    public SimpleResponse update(Long id, RestaurantRequest request) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new BadCredentialException(
                        String.format("Restaurant with id: %s successfully updated", id)));
        if (request.service()>50){
            throw new BadCredentialException("Service cannot exceed 50%");
        }
        restaurant.setName(request.name());
        restaurant.setLocation(request.location());
        restaurant.setRestType(request.restType());
        restaurant.setService(request.service());

        repository.save(restaurant);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Restaurant with id: %s successfully updated",id))
                .build();
    }


    @Override
    public String count() {
        List<UserResponse> allUsers = userService.findAllUsers(repository.findRestaurant().getId());
        int countChef = 0;
        int countWaiter = 0;
        for (UserResponse allUser : allUsers) {
            if (allUser.role().equals(Role.WAITER)) {
                countWaiter++;
            }
            if (allUser.role().equals(Role.CHEF)) {
                countChef++;
            }
        }
        return "Currently the restaurant has " + allUsers.size() + " employees .\n" +
                "Chefs: " + countChef +
                "\nWaiters: " + countWaiter;
    }

}
