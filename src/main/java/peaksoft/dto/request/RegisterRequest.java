package peaksoft.dto.request;

import jakarta.validation.constraints.*;
import peaksoft.enums.Role;
import peaksoft.validation.annotations.PasswordValid;
import peaksoft.validation.annotations.PhoneNumberValid;

import java.time.LocalDate;

public record RegisterRequest(
        @NotEmpty(message = "firstName cannot be empty!")
        String firstName,
        @NotEmpty(message = "lastName cannot be empty!")
        String lastName,
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,
        @NotEmpty(message = "email cannot be empty!")
        @Email(message = "Invalid email!")
        String email,
        @NotEmpty(message = "password cannot be empty!")
        @PasswordValid(message = "password length        min - 4 !")
        String password,

        @NotEmpty(message = "phoneNumber cannot be empty!")
        @Pattern(regexp = "\\+996\\d{9}", message = "Invalid phone number format!")
        @PhoneNumberValid(message = "phone number length == 13 !")
        String phoneNumber,
        @NotNull
        Role role,
        @NotNull
        Integer experience
) {
}