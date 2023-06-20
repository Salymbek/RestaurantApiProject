package peaksoft.dto.response;

import peaksoft.enums.Role;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String fullName,
        long dateOfBirth,
        String email,
        String phoneNumber,
        Role role,
        Integer experience

) {
}