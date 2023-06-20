package peaksoft.dto.pagination;

import lombok.Getter;
import lombok.Setter;
import peaksoft.dto.response.MenuItemResponse;

import java.util.List;

@Setter
@Getter
public class MenuItemPagination {
    private List<MenuItemResponse> menuItems;
    private int currentPage;
    private int pageSize;
}
