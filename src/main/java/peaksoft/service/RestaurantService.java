package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.RestaurantResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse getById(Long id);

    SimpleResponse save(RestaurantRequest request);

    SimpleResponse delete(Long id);
    SimpleResponse update(Long id,RestaurantRequest request);
    String count();
}
