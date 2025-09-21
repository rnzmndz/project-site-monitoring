package site.renzoproject.config_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Use this on production
//@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/actuator/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic();
//        return http.build();
//    }
}
