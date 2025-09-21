package site.renzoproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.renzoproject.config.EmployeeModuleConfig;

@SpringBootApplication
@Import(EmployeeModuleConfig.class)
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping
    public String Hello(){
        return "this backend root endpoint";
    }
}
