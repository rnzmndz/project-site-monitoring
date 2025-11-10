package site.renzoproject.gateway_service.service;

import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class CookieToAuthHeaderFilter implements WebFilter, Ordered {

    // Ordered interface allows us to define the order in which filters are executed
    @Override
    public int getOrder() {
        // HIGHEST_PRECEDENCE ensures this filter runs before most other filters
        return Ordered.HIGHEST_PRECEDENCE;
    }

    // Main filter method executed for every incoming HTTP request
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Retrieve the first cookie with name "ACCESS_TOKEN" from the request
        HttpCookie accessCookie = exchange.getRequest()
                .getCookies()
                .getFirst("ACCESS_TOKEN");

        // If the cookie exists, extract its value (the JWT token)
        if (accessCookie != null) {
            String token = accessCookie.getValue();

            // Mutate the current request to include the token as a Bearer token in the Authorization header
            // This is important because many Spring Security configurations expect the token in the Authorization header
            exchange = exchange.mutate()
                    .request(r -> r.headers(h -> h.setBearerAuth(token)))
                    .build();
        }

        // Continue the filter chain with the (possibly modified) exchange
        return chain.filter(exchange);
    }
}

