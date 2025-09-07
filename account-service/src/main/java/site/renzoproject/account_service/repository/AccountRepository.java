package site.renzoproject.account_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.renzoproject.account_service.model.Account;
import site.renzoproject.account_service.model.AccountStatus;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    @Query("SELECT a FROM Account a WHERE " +
            "LOWER(a.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.lastName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.contactInformation.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.contactInformation.phoneNumber) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Account> searchAccounts(@Param("query") String query, Pageable pageable);

    Page<Account> findByStatus(AccountStatus status, Pageable pageable);
    Page<Account> findByStatusNot(AccountStatus status, Pageable pageable);

    boolean existsByContactInformationEmail(String email);

    boolean existsByContactInformationPhoneNumber(String phoneNumber);

    boolean existsByContactInformationEmailAndStatusNot(String email, AccountStatus accountStatus);

    boolean existsByContactInformationPhoneNumberAndStatusNot(String phoneNumber, AccountStatus accountStatus);
}
