package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record AllRestaurant(
        Long id,
        String name,
        String location,
        String restType
) {
}
