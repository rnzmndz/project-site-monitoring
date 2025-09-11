package site.renzoproject.auth_service.client;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import site.renzoproject.auth_service.dto.account.AccountCreateDto;
import site.renzoproject.auth_service.dto.account.AccountResponseDto;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PostMapping("/api/v1/accounts")
    AccountResponseDto createAccount(@Valid @RequestBody AccountCreateDto accountCreateDto);
}
