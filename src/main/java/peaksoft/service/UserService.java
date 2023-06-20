package peaksoft.service;

import peaksoft.dto.pagination.UserPagination;
import peaksoft.dto.request.ApplicationRequest;
import peaksoft.dto.request.RegisterRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.TokenResponse;
import peaksoft.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    TokenResponse authenticate(UserRequest userRequest);

    SimpleResponse saveUser(RegisterRequest request);
    List<UserResponse> findAll();
    List<UserResponse> getAllApplications();

    SimpleResponse applicationAccept(Long restId, ApplicationRequest request);


    SimpleResponse update(Long id, RegisterRequest request);

    SimpleResponse deleteById(Long id);


    List<UserResponse> findAllUsers(Long restaurantId);

   // UserPagination getUserPagination(int page, int size);
}
