package site.renzoproject.account_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for updating an account")
public class AccountUpdateDto {

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be less than 50 characters")
    @Schema(description = "Employee's first name", example = "John")
    private String firstName;

    @Size(max = 50, message = "Middle name must be less than 50 characters")
    @Schema(description = "Employee's middle name", example = "Michael", nullable = true)
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be less than 50 characters")
    @Schema(description = "Employee's last name", example = "Doe")
    private String lastName;

    @Size(max = 10, message = "Name suffix must be less than 10 characters")
    @Schema(description = "Employee's name suffix", example = "Jr.", nullable = true)
    private String nameSuffix;

    @Schema(description = "Employee's gender", example = "Male", nullable = true)
    private String gender;

    @Schema(description = "URL of employee's profile image", example = "https://example.com/profile.jpg", nullable = true)
    private String imageUrl;

    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    @Schema(description = "Employee's birth date", example = "1990-05-20")
    private LocalDate birthDate;

    @NotNull(message = "Address is required")
    @Schema(description = "Employee's address information")
    private AddressDto addressDto;

    @NotNull(message = "Contact information is required")
    @Schema(description = "Employee's contact information")
    private ContactInformationDto contactInformationDto;

    @NotNull(message = "Emergency contact is required")
    @Schema(description = "Employee's emergency contact information")
    private EmergencyContactDto emergencyContactDto;
}
