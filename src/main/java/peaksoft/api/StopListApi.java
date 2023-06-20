package peaksoft.api;


import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;
import peaksoft.service.StopListService;

import java.util.List;

@RestController
@RequestMapping("/api/stopList")
public class StopListApi {

    private final StopListService stopListService;

    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }


    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveStopList(@RequestBody @Valid StopListRequest request){
        return stopListService.save(request);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<StopListResponse> findAll(){
        return stopListService.findStopLists();
    }

    @PutMapping("/{stopListId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse update(@PathVariable Long stopListId,
                          @RequestBody @Valid StopListRequest stopListRequest) {
        return stopListService.update(stopListId, stopListRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    SimpleResponse delete(@PathVariable Long id){
        return stopListService.delete(id);
    }


//    @GetMapping("/pagination")
//    public StopListPagination getStopListPage(@RequestParam int page,
//                                              @RequestParam int size){
//        return stopListService.getStopListPagination(page,size);
//    }

}