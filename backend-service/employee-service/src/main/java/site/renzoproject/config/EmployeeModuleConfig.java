package site.renzoproject.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


// Can be remove if it convert into microservice
@Configuration
@ComponentScan("site.renzoproject.employee")
public class EmployeeModuleConfig {
}
