package site.renzoproject.account_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import site.renzoproject.account_service.model.AccountStatus;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for creating an account")
public class AccountCreateDto {

    @Schema(description = "Account's I.D.")
    private UUID id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    @Schema(description = "Account user's first name", example = "John")
    private String firstName;

    @Size(max = 50, message = "Middle name must be less than 50 characters")
    @Schema(description = "Account user's middle name", example = "Michael", nullable = true)
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    @Schema(description = "Account user's last name", example = "Doe")
    private String lastName;

    @Size(max = 10, message = "Name suffix must be less than 10 characters")
    @Schema(description = "Account user's name suffix", example = "Jr.", nullable = true)
    private String nameSuffix;

    @Schema(description = "Account user's gender", example = "Male", nullable = true)
    private String gender;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @Schema(description = "Account user's birth date", example = "1990-05-20")
    private LocalDate birthDate;

    @Schema(description = "URL of account user's profile image", example = "https://example.com/profile.jpg", nullable = true)
    private String imageUrl;

    @Schema(description = "Account status", example = "ACTIVE", defaultValue = "ACTIVE")
    private AccountStatus status = AccountStatus.ACTIVE;


    @NotNull(message = "Address is required")
    @Schema(description = "Account user's address information")
    private AddressDto addressDto;

    @NotNull(message = "Contact information is required")
    @Schema(description = "Account user's contact information")
    private ContactInformationDto contactInformationDto;

    @NotNull(message = "Emergency contact is required")
    @Schema(description = "Account user's emergency contact information")
    private EmergencyContactDto emergencyContactDto;
}
