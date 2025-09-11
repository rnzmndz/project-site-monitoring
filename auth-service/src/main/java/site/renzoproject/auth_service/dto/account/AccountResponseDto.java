package site.renzoproject.auth_service.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response object for an account")
public class AccountResponseDto {
    @Schema(description = "Account id", example = "asdf234fffq23wer234faf")
    private UUID id;

    @Schema(description = "Account first name", example = "John")
    private String firstName;

    @Schema(description = "Account middle name", example = "Michael")
    private String middleName;

    @Schema(description = "Account last name", example = "Doe")
    private String lastName;

    @Schema(description = "Account name suffix", example = "Jr.")
    private String nameSuffix;

    @Schema(description = "Account gender", example = "Male")
    private String gender;

    @Schema(description = "URL of Account profile image", example = "https://example.com/profile.jpg", nullable = true)
    private String imageUrl;

    @Schema(description = "Account birth date", example = "1990-05-20")
    private LocalDate birthDate;

    @Schema(description = "Account status", example = "ACTIVE")
    private AccountStatus status;

    @Schema(description = "Account address information")
    private AddressDto addressDto;

    @Schema(description = "Account contact information")
    private ContactInformationDto contactInformationDto;

    @Schema(description = "Account emergency contact information")
    private EmergencyContactDto emergencyContactDto;

    @Schema(description = "Timestamp when Account was created", example = "2023-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Timestamp when Account was last updated", example = "2023-01-20T15:45:00")
    private LocalDateTime updatedAt;

    @Schema(description = "User who created the Account record", example = "admin@company.com")
    private String createdBy;

    @Schema(description = "User who last modified the Account record", example = "hr@company.com")
    private String modifiedBy;
}
