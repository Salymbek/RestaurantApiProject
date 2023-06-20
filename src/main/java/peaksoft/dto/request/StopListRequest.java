package peaksoft.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record StopListRequest(
        @NotEmpty(message = " Reason must not be empty!")
        String reason,
        @FutureOrPresent(message = "The date must be in the future or present!")
        LocalDate date,
        Long menuItemId
) {
}