package site.renzoproject.account_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public SecurityConfig(@Lazy JwtAuthenticationConverter jwtAuthenticationConverter) {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            // Permit open endpoints
            auth
                    .requestMatchers("/public/**", "/debug").permitAll()
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();

            // Dynamically register secured endpoints
            SECURED_ENDPOINTS.forEach((method, endpoints) -> {
                endpoints.forEach((pattern, role) -> {
                    auth.requestMatchers(method, pattern).hasAuthority(role);
                });
            });

            // All other endpoints require auth
            auth.anyRequest().authenticated();
        });

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
        );

        return http.build();
    }



    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");
            if (realmAccess == null || !(realmAccess.get("roles") instanceof List<?> roles)) {
                return List.of();
            }

            return roles.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(role -> "ROLE_" + role)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        });
        return converter;
    }

    private static final Map<HttpMethod, Map<String, String>> SECURED_ENDPOINTS = Map.of(
            HttpMethod.GET, Map.of(
                    "/api/v1/accounts", "ROLE_VIEW_ACCOUNT_LIST",
                    "/api/v1/accounts/*", "ROLE_VIEW_ACCOUNT_DETAIL",
                    "/api/v1/accounts/sorted", "ROLE_VIEW_ACCOUNT_LIST",
                    "/api/v1/accounts/search", "ROLE_VIEW_ACCOUNT_LIST"
            ),
            HttpMethod.POST, Map.of("/api/v1/accounts", "ROLE_CREATE_ACCOUNT"),
            HttpMethod.PATCH, Map.of(
                    "/api/v1/accounts/*/suspend", "ROLE_ACCOUNT_UPDATE_STATUS",
                    "/api/v1/accounts/*/status", "ROLE_ACCOUNT_UPDATE_STATUS",
                    "/api/v1/accounts/*/enable", "ROLE_ACCOUNT_UPDATE_STATUS",
                    "/api/v1/accounts/*/disable", "ROLE_ACCOUNT_UPDATE_STATUS"
            ),
            HttpMethod.PUT, Map.of(
                    "/api/v1/accounts/*", "ROLE_ACCOUNT_UPDATE"
            ),
            HttpMethod.DELETE, Map.of(
                    "/api/v1/accounts/*", "ROLE_ACCOUNT_DELETE"
            )
    );


}
