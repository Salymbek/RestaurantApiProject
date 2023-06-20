package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.pagination.StopListPagination;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.exception.NotFoundException;
import peaksoft.model.MenuItem;
import peaksoft.model.StopList;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;


import java.util.List;

@Service
@Transactional
public class StopListServiceImpl implements StopListService {


    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }


    @Override
    public SimpleResponse save(StopListRequest request) {
        MenuItem menuItem = menuItemRepository.findById(request.menuItemId()).orElseThrow(
                () -> new NotFoundException("This id:" + request.menuItemId() + " does not exist"));

        StopList stopList = new StopList();
        stopList.setDate(request.date());
        stopList.setReason(request.reason());



        boolean exists = stopListRepository.existsByDateAndMenuItems_Name(request.date(),menuItem.getName());
        if (exists){
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .message(String.format("The MenuItem named %s has already been added to the stop list on this date",menuItem.getName()))
                    .build();
        }

        stopList.setMenuItems(menuItem);
        stopListRepository.save(stopList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("Stop list with id: %d successfully saved", stopList.getId())).build();
    }

    @Override
    public List<StopListResponse> findStopLists() {
        return stopListRepository.findAllStopList();
    }

    @Override
    public SimpleResponse update(Long stopListId, StopListRequest request) {
        StopList stopList = stopListRepository.findById(stopListId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("StopList with id: %s not found!",stopListId)));

        boolean exists = stopListRepository.existsByMenuItems_NameAndDateAndIdNot(stopList.getMenuItems().getName(), request.date(), stopListId);
        if (exists){
            return SimpleResponse.builder()
                    .status(HttpStatus.CONFLICT)
                    .message(String.format("The MenuItem named %s has already been added to the stop list on this date",stopList.getMenuItems().getName()))
                    .build();
        }

        stopList.setReason(request.reason());
        stopList.setDate(request.date());
        stopListRepository.save(stopList);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message(String.format("StopList with id: %s successfully updated",stopListId))
                .build();
    }


    @Override
    public SimpleResponse delete(Long stopListId) {
        if (!stopListRepository.existsById(stopListId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Stop list with id - " + stopListId + " doesn't exists!")
                    .build();
        }
        StopList stopList = stopListRepository.findById(stopListId).orElseThrow(() ->
                new NotFoundException("Stop list with id - " + stopListId + " doesn't exists!"));
        //
        stopList.setMenuItems(null);
        //
        stopListRepository.delete(stopList);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Stop list with id - " + stopListId + " is deleted!")
                .build();
    }

//    @Override
//    public StopListPagination getStopListPagination(int page, int size) {
//        Pageable pageable = PageRequest.of(page-1, size);
//        Page<StopList> all = stopListRepository.findAll(pageable);
//
//        StopListPagination paginationStopListResponse = new StopListPagination();
//     //   paginationStopListResponse.setStopLists(all.getContent());
//        paginationStopListResponse.setCurrentPage(pageable.getPageNumber()+1);
//        paginationStopListResponse.setPageSize(all.getTotalPages());
//        return paginationStopListResponse;
//    }
}