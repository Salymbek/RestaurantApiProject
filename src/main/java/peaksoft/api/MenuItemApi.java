package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.pagination.MenuItemPagination;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemAllResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.MenuItemService;

import java.util.List;

@RestController
@RequestMapping("/api/menuItem")
public class MenuItemApi {

    private final MenuItemService menuItemService;

    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CHEF')")
    SimpleResponse save(@RequestBody @Valid MenuItemRequest menuItemRequest){
        return menuItemService.saveManu(menuItemRequest);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    List<MenuItemAllResponse> findAll(){
        return menuItemService.findAllMenu();
    }

    @PutMapping("/{menuItemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse update(@PathVariable  Long menuItemId,
                          @RequestBody @Valid MenuItemRequest menuItemRequest){
        return menuItemService.updateMenu(menuItemId, menuItemRequest);
    }

    @DeleteMapping("/{menuItemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse delete(@PathVariable Long menuItemId){
        return menuItemService.deleteManu(menuItemId);
    }

    @GetMapping("/sort")
    @PreAuthorize("permitAll()")
    List<MenuItemResponse> sort(@RequestParam(required = false) String ascOrDesc) {
        return menuItemService.sort(ascOrDesc);
    }

    @GetMapping("/filter")
    @PreAuthorize("permitAll()")
    List<MenuItemResponse> isVegetarian(@RequestParam boolean isTrue){
        return menuItemService.sortIsVegetarian(isTrue);
    }

//    @GetMapping("/pagination")
//    public MenuItemPagination getMenuItemPage(@RequestParam int page, @RequestParam int size){
//        return menuItemService.getMenuItemPagination(page,size);
//    }

    @GetMapping("/search")
    public List<MenuItemAllResponse> search (@RequestParam(required = false) String keyWord){
        return menuItemService.globalSearch(keyWord);
    }


}