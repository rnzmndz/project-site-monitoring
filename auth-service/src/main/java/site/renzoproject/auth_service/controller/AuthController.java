package site.renzoproject.auth_service.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import site.renzoproject.auth_service.dto.LoginRequest;
import site.renzoproject.auth_service.dto.RegisterRequest;
import site.renzoproject.auth_service.dto.RegisterResponse;
import site.renzoproject.auth_service.dto.TokenResponse;
import site.renzoproject.auth_service.service.AuthService;
import site.renzoproject.auth_service.service.KeycloakService;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AuthController {

    private final AuthService authService;
    private final KeycloakService keycloakService;

    @Value("${KC_TOKEN_URI}")
    private String tokenUri;

    private final String clientId = "auth-service";

    @Value("${KC_CLIENT_SECRET}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // Build form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", request.getUsername());
        formData.add("password", request.getPassword());
        formData.add("scope", "openid email profile");

        // Build request entity
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

        // Send the request
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                tokenUri,
                httpEntity,
                TokenResponse.class
        );

        // Recently Added Under Observation we can delete this block if this wont work
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            TokenResponse tokenResponse = response.getBody();

            // Create secure HttpOnly cookies
            ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", tokenResponse.getAccess_token())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(Duration.ofMinutes(2))
                    .build();

            ResponseCookie refreshCookie = ResponseCookie.from("REFRESH_TOKEN", tokenResponse.getRefresh_token())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None")
                    .path("/")
                    .maxAge(Duration.ofDays(7))
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                    .build();
        }

//        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/token")
    public String getToken(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getTokenValue();
    }

    @GetMapping("/roles/me")
    public List<String> getMyUserRoles(Authentication authentication){
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            String userId = jwt.getClaim("sub");
            return keycloakService.getUserRoles(userId);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No JWT found in security context");
    }

//    @GetMapping("/user-info")
//    public AccountDetails getAccountDetails(Authentication authentication) {
//        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//            Jwt jwt = jwtAuth.getToken();
//            return AccountDetails.builder()
//                    .userId(jwt.getClaim("sub"))
//                    .username(jwt.getClaim("preferred_username"))
//                    .fullName(jwt.getClaim("name"))
//                    .firstName(jwt.getClaim("given_name"))
//                    .lastName(jwt.getClaim("family_name"))
//                    .email(jwt.getClaim("email"))
//                    .build();
//        }
//        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No JWT found in security context");
//    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshToken(@CookieValue(name = "REFRESH_TOKEN", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Prepare form data
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                tokenUri,
                httpEntity,
                TokenResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            TokenResponse tokenResponse = response.getBody();

            ResponseCookie accessCookie = ResponseCookie.from("ACCESS_TOKEN", tokenResponse.getAccess_token())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite("None") // Set Strict if prod
//                    .sameSite("Lax") // for dev mode
                    .path("/")
                    .maxAge(Duration.ofMinutes(2))
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                    .build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie deleteAccess = ResponseCookie.from("ACCESS_TOKEN", "")
                .httpOnly(true)
                .secure(true) // Set true if prod
                .sameSite("None") // Set Strict if prod
                .path("/")
                .maxAge(0)
                .build();

        ResponseCookie deleteRefresh = ResponseCookie.from("REFRESH_TOKEN", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None") // Set Strict if prod
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteAccess.toString())
                .header(HttpHeaders.SET_COOKIE, deleteRefresh.toString())
                .build();
    }

    @GetMapping("/session")
    public ResponseEntity<?> checkSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAuthenticated = authentication != null &&
                authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal());

        return ResponseEntity.ok(Map.of("authenticated", isAuthenticated));
    }

    @GetMapping("/{userId}/username")
    public ResponseEntity<String> getUsername(@PathVariable String userId) {
        try {
            String username = keycloakService.getUsername(userId);
            return ResponseEntity.ok(username);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
