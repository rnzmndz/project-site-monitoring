package site.renzoproject.account_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.renzoproject.account_service.model.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
}
