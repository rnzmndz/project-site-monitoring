package site.renzoproject.auth_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.renzoproject.auth_service.client.AccountClient;
import site.renzoproject.auth_service.dto.RegisterRequest;
import site.renzoproject.auth_service.dto.RegisterResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final KeycloakService keycloakService;
    private final AccountClient accountClient;

    public RegisterResponse register(RegisterRequest request) {
        try {
            // Validate email doesn't already exist
            if (keycloakService.isEmailExisting(request.getAccountCreateDto().getContactInformationDto().getEmail())) {
                throw new RuntimeException("Email already exists");
            }

            // Create user in Keycloak first
            String keycloakId = keycloakService.createUser(request);
            UUID userId = UUID.fromString(keycloakId);

            // Wait a bit longer to ensure Keycloak fully processes the user
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Registration process interrupted", e);
            }

            // Create employee record
            request.getAccountCreateDto().setId(userId);
            try {
                accountClient.createAccount(request.getAccountCreateDto());
            } catch (Exception e) {
                log.error("Failed to create employee record for user {}: {}", userId, e.getMessage());
                // You might want to implement compensation logic here
                throw new RuntimeException("Failed to create employee record", e);
            }

            log.info("Successfully registered user with ID: {}", userId);
            return new RegisterResponse(userId, "User registered successfully");

        } catch (Exception e) {
            log.error("Registration failed for user {}: {}", request.getUsername(), e.getMessage());
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        }
    }
}
