package site.renzoproject.gateway_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String jwtIssuerUri;

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        http
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
//                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .pathMatchers(
//                                "/public/**",
//                                "/auth/login", "/auth/register", "/auth/refresh", "/auth/session",
//                                "/v3/api-docs/**", "/api-docs/**",
//                                "/swagger-ui.html",
//                                "/swagger-ui/**",
//                                "/webjars/swagger-ui/**"
//                        ).permitAll()
                                .pathMatchers(HttpMethod.GET, "/employee-service/v3/api-docs").permitAll()
//                        .anyExchange().authenticated()
                                .anyExchange().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(unauthorizedEntryPoint())
                );

        return http.build();
    }

    @Bean
    public ServerAuthenticationEntryPoint unauthorizedEntryPoint() {
        return (exchange, ex) -> {

            // Get the HTTP response object from the reactive exchange
            var response = exchange.getResponse();

            // Set the HTTP status to 401 Unauthorized (client is not authenticated)
            response.setStatusCode(HttpStatus.UNAUTHORIZED);

            // Return the response in JSON format instead of HTML or plain text
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

            // Extract the request's Origin header (used for CORS checks)
            String origin = exchange.getRequest().getHeaders().getOrigin();

            // Check if the request came from one of the allowed frontend domains
            // This prevents unauthorized domains from making cross-site requests
            if (origin != null && List.of(
                    "http://localhost:4200",          // Local Angular development
                    "https://app.renzoproject.site", // Production frontend app
                    "https://api.renzoproject.site"  // Optional web app domain
            ).contains(origin)) {

                // Allow that origin to access the response
                response.getHeaders().add("Access-Control-Allow-Origin", origin);

                // Inform caching proxies that the response may vary depending on Origin
                response.getHeaders().add("Vary", "Origin");
            }

            // Allow cookies (e.g., HttpOnly session cookies) to be included in cross-origin requests
            // Necessary when using Keycloak/Spring with cookies for authentication
            response.getHeaders().add("Access-Control-Allow-Credentials", "true");

            // Allow specific request headers to be sent by the client (e.g., Authorization tokens)
            response.getHeaders().add("Access-Control-Allow-Headers", "Authorization, Content-Type");

            // Allow these HTTP methods when responding to preflight or unauthorized requests
            response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");

            // This is the JSON message that will be returned to the client
            byte[] bytes = "{\"error\": \"Unauthorized\"}".getBytes(StandardCharsets.UTF_8);

            // Wrap the message as a data buffer for the reactive HTTP response
            var buffer = response.bufferFactory().wrap(bytes);

            // Send the response body and return it as a reactive Mono
            return response.writeWith(Mono.just(buffer));
        };
    }
}
