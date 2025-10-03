package site.renzoproject.employee_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import site.renzoproject.employee_service.dto.account.AccountResponseDto;

import java.util.UUID;

@FeignClient(name = "account-service")
public interface AccountClient {

    @GetMapping("/api/v1/accounts/{id}")
    public AccountResponseDto getAccountById(@PathVariable UUID id);
}
