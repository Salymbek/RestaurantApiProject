package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.MenuItemAllResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.exception.AlreadyExistException;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Cheque;
import peaksoft.model.MenuItem;

import peaksoft.model.Subcategory;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final ChequeRepository chequeRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, SubcategoryRepository subcategoryRepository, ChequeRepository chequeRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subcategoryRepository = subcategoryRepository;
        this.chequeRepository = chequeRepository;
    }


    @Override
    public SimpleResponse saveManu(MenuItemRequest menuItemRequest) {
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price can't be negative number!")
                    .build();
        }
        if (menuItemRepository.existsByName(menuItemRequest.name())) {
            throw new AlreadyExistException("Menu item name: " + menuItemRequest.name() + " already exist!!!");
        }
        Subcategory subcategory = subcategoryRepository.findByName(menuItemRequest.subcategory())
                .orElseThrow(() -> new NotFoundException(
                        String.format("SubCategory with id: %s not found", menuItemRequest.subcategory())));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        menuItem.setRestaurant(restaurantRepository.findRestaurants().orElseThrow(()-> new NotFoundException("Restaurant is null")));
        menuItem.setSubcategory(subcategory);

        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("MenuItem with name: %s successfully saved", menuItem.getName()))
                .build();
    }

    @Override
    public List<MenuItemAllResponse> findAllMenu() {
        return menuItemRepository.findAllMenuItem();
    }

    @Override
    public List<MenuItemResponse> sort(String ascOrDesc) {
        if (ascOrDesc.equals("desc")){
            return menuItemRepository.descSorting();
        } else {
            return menuItemRepository.ascSorting();
        }
    }

    @Override
    public SimpleResponse updateMenu(Long menuId, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuId).orElseThrow(
                () -> new NotFoundException("Menu with id - " + menuId + " is not found!"));
        if (menuItemRequest.price().intValue() < 0) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price can't be negative number!")
                    .build();
        }
        Subcategory subcategory = subcategoryRepository.findByName(menuItemRequest.subcategory())
                .orElseThrow(() -> new NotFoundException(
                        String.format("SubCategory with id: %s not found", menuItemRequest.subcategory())));

        menuItem.setName(menuItemRequest.name());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setIsVegetarian(menuItemRequest.isVegetarian());
        menuItem.setSubcategory(subcategory);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Menu item - %s is updated!",menuItem.getName()))
                .build();
    }



    @Override
    public SimpleResponse deleteManu(Long id) {
        if (!menuItemRepository.existsById(id)){
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("StopList with id: %s not found",id))
                    .build();
        }
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("MenuItem with id: "+id+" not found"));

        chequeRepository.deleteAll(menuItem.getCheques());
        menuItemRepository.delete(menuItem);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Menu with id: %d id successfully deleted", id)).build();
    }

    @Override
    public List<MenuItemResponse> sortIsVegetarian(boolean isTrue) {
        return menuItemRepository.findMenuItemByIsVegetarian(isTrue);
    }

//    @Override
//    public MenuItemPagination getMenuItemPagination(int page, int size) {
//        Pageable pageable = PageRequest.of(page-1, size);
//        Page<MenuItem> all = menuItemRepository.findAll(pageable);
//
//        MenuItemPagination paginationMenuResponse = new MenuItemPagination();
//     //   paginationMenuResponse.setMenuItems(all.getContent());
//        paginationMenuResponse.setCurrentPage(pageable.getPageNumber()+1);
//        paginationMenuResponse.setPageSize(all.getTotalPages());
//        return paginationMenuResponse;
//
//    }

    @Override
    public List<MenuItemAllResponse> globalSearch(String keyWord) {
        List<MenuItemAllResponse> list = new ArrayList<>();
        if (keyWord == null){
            list.addAll(menuItemRepository.findAllMenuItem());
            return list;
        }
        list.addAll(menuItemRepository.AllSearch(keyWord));
        return list;
    }
}