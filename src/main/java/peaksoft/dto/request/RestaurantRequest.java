package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RestaurantRequest(
        @NotEmpty
        String name,
        @NotEmpty
        String location,
        String restType,
        int service
) {
}