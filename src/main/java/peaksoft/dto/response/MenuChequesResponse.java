package peaksoft.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuChequesResponse(
        Long id,
        String name,
        BigDecimal price,
        Long amount

) {
}