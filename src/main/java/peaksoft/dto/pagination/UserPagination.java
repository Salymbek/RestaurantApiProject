package peaksoft.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.UserResponse;

import java.util.List;

@Setter
@Getter
public class UserPagination {
    private List<UserResponse> users;
    private int currentPage;
    private int pageSize;
}
