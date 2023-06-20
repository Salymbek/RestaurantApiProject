package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
public class ChequeResponse{
    private Long id;
    private LocalDate date;
    private String waiter;
    private List<MenuChequesResponse> meals;
    private BigDecimal averagePrice;
    private int service;
    private BigDecimal grandTotal;


    public ChequeResponse(Long id, LocalDate date, String waiter, BigDecimal averagePrice, int service) {
        this.id = id;
        this.date = date;
        this.waiter = waiter;
        this.averagePrice = averagePrice;
        this.service = service;
    }


}