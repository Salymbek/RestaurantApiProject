package peaksoft.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListResponse(
        Long id,
        String menuItemName,
        String reason,
        LocalDate date
) {
}