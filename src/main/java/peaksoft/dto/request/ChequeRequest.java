package peaksoft.dto.request;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ChequeRequest(
        @NotEmpty
        List<Long> foodsId
) {
}
