package peaksoft.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.StopListResponse;

import java.util.List;
@Setter
@Getter
public class StopListPagination {
    private List<StopListResponse> stopLists;
    private int currentPage;
    private int pageSize;
}
