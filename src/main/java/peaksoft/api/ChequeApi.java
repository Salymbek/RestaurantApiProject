package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.response.*;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cheque")
public class ChequeApi {

    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }


    @PostMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public SimpleResponse save(@PathVariable  Long userId,
                               @RequestBody @Valid ChequeRequest request){
        return chequeService.save(userId,request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse delete(@PathVariable Long id){
        return chequeService.delete(id);
    }

    @PutMapping("/{chequeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    SimpleResponse update(@PathVariable Long chequeId,
                          @RequestBody @Valid ChequeRequest chequeRequest) {
        return chequeService.update(chequeId, chequeRequest);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("permitAll()")
    List<ChequeResponse> getAll(@PathVariable Long userId){
        return chequeService.findAll(userId);
    }

    @GetMapping("/allSum/user_id/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'WAITER')")
    AllChequesSum sumAllCheque(@PathVariable Long userId,
                               @RequestParam LocalDate date){
        return chequeService.totalSum(userId, date);
    }

    @GetMapping("/avg")
    @PreAuthorize("permitAll()")
    public SimpleResponse avg(@RequestParam LocalDate date){
        return chequeService.avg(date);
    }

}