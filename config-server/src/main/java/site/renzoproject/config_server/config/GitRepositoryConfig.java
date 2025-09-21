package site.renzoproject.config_server.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.config.server.environment.JGitEnvironmentProperties;
import org.springframework.cloud.config.server.environment.JGitEnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

// Use this in production
//@Configuration
public class GitRepositoryConfig {

//    @Value("${spring.cloud.config.server.git.uri}")
//    private String gitUri;
//
//    @Value("${spring.cloud.config.server.git.username:}")
//    private String username;
//
//    @Value("${spring.cloud.config.server.git.password:}")
//    private String password;
//
//    @Bean
//    public JGitEnvironmentRepository jGitEnvironmentRepository(ConfigurableEnvironment environment, JGitEnvironmentProperties properties, ObservationRegistry observationRegistry) {
//        properties.setUri(gitUri);
//        properties.setUsername(username);
//        properties.setPassword(password);
//        return new JGitEnvironmentRepository(environment, properties, observationRegistry);
//    }
}
