package peaksoft.api;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.pagination.UserPagination;
import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.*;

import peaksoft.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public SimpleResponse saveUser(@RequestBody @Valid RegisterRequest request){
        return userService.saveUser(request);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid UserRequest request) {
        return userService.authenticate(request);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<UserResponse> getAll(){
        return userService.findAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateUser(@PathVariable Long id, @RequestBody @Valid RegisterRequest request){
        return userService.update(id,request);
    }
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return userService.deleteById(id);
    }

    @GetMapping("/applications")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserResponse>getAllApplications(){
        return userService.getAllApplications();
    }

    @PostMapping("/accept/restaurant_id/{restaurantId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse acceptResponse(@PathVariable Long restaurantId,
                                  @RequestBody ApplicationRequest request){
        return userService.applicationAccept(restaurantId, request);
    }

//    @GetMapping("/pagination")
//    public UserPagination getUserPage(@RequestParam int page,
//                                      @RequestParam int size){
//        return userService.getUserPagination(page,size);
//    }


}