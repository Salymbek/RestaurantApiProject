package peaksoft.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class AllChequesSum{
    private String waiter;
    private LocalDate date;
    private Long counterOfCheque;
    private BigDecimal totalSumma;

    public AllChequesSum(String waiter, LocalDate date) {
        this.waiter = waiter;
        this.date = date;
    }

    public AllChequesSum() {
    }
}