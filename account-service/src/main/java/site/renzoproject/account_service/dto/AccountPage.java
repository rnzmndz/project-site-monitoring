package site.renzoproject.account_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Schema(name = "EmployeePage")
public class AccountPage extends PageImpl<AccountListDto> {
    public AccountPage(List<AccountListDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}
