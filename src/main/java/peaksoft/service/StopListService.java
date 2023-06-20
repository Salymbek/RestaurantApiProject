package peaksoft.service;

import peaksoft.dto.pagination.StopListPagination;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.StopListResponse;

import java.util.List;

public interface StopListService {
    SimpleResponse save(StopListRequest request);
    List<StopListResponse> findStopLists ();
    SimpleResponse update(Long stopListId ,StopListRequest request);
    SimpleResponse delete( Long stopList);

 //   StopListPagination getStopListPagination(int page, int size);
}
