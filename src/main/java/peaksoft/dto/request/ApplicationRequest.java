package peaksoft.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NonNull;
@Builder
public record ApplicationRequest(
        @NotNull
        Long userId,
        @NonNull
        Boolean accept
) {
}
