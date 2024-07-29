package seominkim.puppyAlert.domain.puppy.dto.response;

import jakarta.validation.constraints.NotBlank;

public record MatchResponse(
        @NotBlank Long foodId,
        @NotBlank String hostId,
        String puppyId
) {}
