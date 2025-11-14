package site.renzoproject.account_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import site.renzoproject.account_service.dto.*;
import site.renzoproject.account_service.model.AccountStatus;
import site.renzoproject.account_service.service.AccountService;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Account management APIs")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create a new account", description = "Creates a new account with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Email or phone number already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(
            @Valid @RequestBody AccountCreateDto createDto) {
        log.info("Creating new account for: {} {}", createDto.getFirstName(), createDto.getLastName());
        AccountResponseDto response = accountService.createAccount(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get account by ID", description = "Retrieves an account by its unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account found",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponseDto> getAccountById(
            @Parameter(description = "Account ID", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable UUID id) {
        log.debug("Fetching account with ID: {}", id);
        AccountResponseDto response = accountService.getAccountById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get authenticated user's account details",
            description = "Retrieves the details of the currently authenticated user based on the JWT token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved user account details",
                    content = @Content(schema = @Schema(implementation = MyAccountDetails.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized – No valid JWT token found in security context"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @GetMapping("/user-info")
    public MyAccountDetails getMyAccountDetails(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            return MyAccountDetails.builder()
                    .userId(jwt.getClaim("sub"))
                    .username(jwt.getClaim("preferred_username"))
                    .fullName(jwt.getClaim("name"))
                    .firstName(jwt.getClaim("given_name"))
                    .lastName(jwt.getClaim("family_name"))
                    .email(jwt.getClaim("email"))
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No JWT found in security context");
    }


    @Operation(summary = "Get all accounts", description = "Retrieves a paginated list of all active accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AccountPage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<AccountPage> getAllAccounts(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field", example = "firstName")
            @RequestParam(defaultValue = "firstName") String sortBy,
            @Parameter(description = "Sort direction", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        log.debug("Fetching all accounts - page: {}, size: {}, sort: {}", page, size, sortBy);
        AccountPage response = accountService.getAllAccounts(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Search accounts", description = "Searches accounts by name, email, or phone number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Search completed successfully",
                    content = @Content(schema = @Schema(implementation = AccountPage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/search")
    public ResponseEntity<AccountPage> searchAccounts(
            @Parameter(description = "Search query", example = "john")
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        log.debug("Searching accounts with query: '{}'", query);
        AccountPage response = accountService.searchAccounts(query, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update account", description = "Updates an existing account with new details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "409", description = "Email or phone number already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDto> updateAccount(
            @Parameter(description = "Account ID")
            @PathVariable UUID id,
            @Valid @RequestBody AccountUpdateDto updateDto) {

        log.info("Updating account with ID: {}", id);
        AccountResponseDto response = accountService.updateAccount(id, updateDto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete account", description = "Soft deletes an account by setting its status to DELETED")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "412", description = "Account cannot be deleted due to business rules"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "Account ID")
            @PathVariable UUID id) {

        log.info("Deleting account with ID: {}", id);
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Disable account", description = "Disables an account by setting its status to INACTIVE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account disabled successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/disable")
    public ResponseEntity<AccountResponseDto> disableAccount(
            @Parameter(description = "Account ID")
            @PathVariable UUID id) {

        log.info("Disabling account with ID: {}", id);
        AccountResponseDto response = accountService.disableAccount(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Enable account", description = "Enables an account by setting its status to ACTIVE")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account enabled successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/enable")
    public ResponseEntity<AccountResponseDto> enableAccount(
            @Parameter(description = "Account ID")
            @PathVariable UUID id) {

        log.info("Enabling account with ID: {}", id);
        AccountResponseDto response = accountService.enableAccount(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Suspend account", description = "Suspends an account by setting its status to SUSPENDED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account suspended successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/suspend")
    public ResponseEntity<AccountResponseDto> suspendAccount(
            @Parameter(description = "Account ID")
            @PathVariable UUID id) {

        log.info("Suspending account with ID: {}", id);
        AccountResponseDto response = accountService.suspendAccount(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update account status", description = "Updates the status of an account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account status updated successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<AccountResponseDto> updateAccountStatus(
            @Parameter(description = "Account ID")
            @PathVariable UUID id,
            @Parameter(description = "New account status", example = "ACTIVE")
            @RequestParam AccountStatus status) {

        log.info("Updating account status to {} for ID: {}", status, id);
        AccountResponseDto response = accountService.updateAccountStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get accounts by status", description = "Retrieves accounts filtered by status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AccountPage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<AccountPage> getAccountsByStatus(
            @Parameter(description = "Account status", example = "ACTIVE")
            @PathVariable AccountStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        log.debug("Fetching accounts with status: {}", status);
        AccountPage response = accountService.getAccountsByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get deleted accounts", description = "Retrieves a list of soft-deleted accounts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted accounts retrieved successfully",
                    content = @Content(schema = @Schema(implementation = AccountPage.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/deleted")
    public ResponseEntity<AccountPage> getDeletedAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        log.debug("Fetching deleted accounts");
        AccountPage response = accountService.getDeletedAccounts(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Check email uniqueness", description = "Checks if an email address is unique")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Uniqueness check completed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailUnique(
            @Parameter(description = "Email address to check", example = "john.doe@example.com")
            @RequestParam String email) {

        log.debug("Checking email uniqueness: {}", email);
        boolean isUnique = accountService.isEmailUnique(email);
        return ResponseEntity.ok(isUnique);
    }

    @Operation(summary = "Check phone uniqueness", description = "Checks if a phone number is unique")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Uniqueness check completed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/check-phone")
    public ResponseEntity<Boolean> checkPhoneUnique(
            @Parameter(description = "Phone number to check", example = "+1234567890")
            @RequestParam String phone) {

        log.debug("Checking phone uniqueness: {}", phone);
        boolean isUnique = accountService.isPhoneUnique(phone);
        return ResponseEntity.ok(isUnique);
    }

    @Operation(summary = "Check account existence", description = "Checks if an account exists by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Existence check completed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkAccountExists(
            @Parameter(description = "Account ID")
            @PathVariable UUID id) {

        log.debug("Checking account existence for ID: {}", id);
        boolean exists = accountService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Check if account is active", description = "Checks if an account has ACTIVE status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active status check completed"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}/active")
    public ResponseEntity<Boolean> isAccountActive(
            @Parameter(description = "Account ID")
            @PathVariable UUID id) {

        log.debug("Checking if account is active for ID: {}", id);
        boolean isActive = accountService.isAccountActive(id);
        return ResponseEntity.ok(isActive);
    }
}
