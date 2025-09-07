package site.renzoproject.account_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Account summary information")
public class AccountListDto {

    @Schema(description = "Account id")
    private UUID id;

    @Schema(description = "Account first name", example = "John")
    private String firstName;

    @Schema(description = "Account middle name", example = "Michael", nullable = true)
    private String middleName;

    @Schema(description = "Account last name", example = "Doe")
    private String lastName;

    @Schema(description = "URL of Account profile image", example = "https://example.com/profile.jpg", nullable = true)
    private String imageUrl;
}
