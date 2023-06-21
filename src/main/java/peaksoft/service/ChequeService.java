package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface ChequeService {
    SimpleResponse save(Long userId, ChequeRequest request);

    List<ChequeResponse> findAll(Long userId);

    SimpleResponse update(Long id, ChequeRequest request);

    SimpleResponse delete(Long id);

    AllChequesSum totalSum(Long userId, LocalDate date);

    SimpleResponse avg(LocalDate date);

}