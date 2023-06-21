package peaksoft.service;

import peaksoft.dto.pagination.StopListPagination;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;


public interface StopListService {
    SimpleResponse save(StopListRequest request);
    StopListPagination getAllStopList(int page, int size);
    SimpleResponse update(Long stopListId ,StopListRequest request);
    SimpleResponse delete( Long stopList);

}
