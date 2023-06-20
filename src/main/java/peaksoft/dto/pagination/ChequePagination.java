package peaksoft.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.ChequeResponse;

import java.util.List;
@Setter
@Getter
public class ChequePagination {
    private List<ChequeResponse> cheques;
    private int currentPage;
    private int pageSize;
}
