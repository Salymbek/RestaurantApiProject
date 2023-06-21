package peaksoft.service;

import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemAllResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    SimpleResponse saveManu(MenuItemRequest menuItemRequest);

    List<MenuItemAllResponse> findAllMenu();

    List<MenuItemResponse> sort(String ascOrDesc);

    SimpleResponse updateMenu(Long id, MenuItemRequest menuItemRequest);

    SimpleResponse deleteManu(Long id);

    List<MenuItemResponse> sortIsVegetarian (boolean isTrue);

    List<MenuItemAllResponse> globalSearch(String keyWord);
}
