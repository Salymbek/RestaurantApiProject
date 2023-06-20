package peaksoft.dto.response;


public record RestaurantResponse(
        Long id,
        String name,
        String location,
        String restType,
        int service
) {
}