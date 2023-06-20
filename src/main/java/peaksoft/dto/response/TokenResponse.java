package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(
        String email,
        String token
) {
}
