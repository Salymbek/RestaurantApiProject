package peaksoft.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.RestaurantResponse;

import java.util.List;

@Setter
@Getter
public class RestaurantPagination {
    private List<RestaurantResponse> restaurants;
    private int currentPage;
    private int pageSize;
}
