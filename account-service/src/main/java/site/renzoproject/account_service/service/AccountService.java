package site.renzoproject.account_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.renzoproject.account_service.dto.*;
import site.renzoproject.account_service.exception.*;
import site.renzoproject.account_service.mapper.AccountMapper;
import site.renzoproject.account_service.model.Account;
import site.renzoproject.account_service.model.AccountStatus;
import site.renzoproject.account_service.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final ValidationService validationService;

    
    @Transactional
    public AccountResponseDto createAccount(AccountCreateDto createDto) {
        try {
            log.info("Creating new account for: {} {}", createDto.getFirstName(), createDto.getLastName());

            // Validate input
            validationService.validateAccountCreateDto(createDto);

            // Check uniqueness
            validateUniqueConstraints(createDto.getContactInformationDto().getEmail(),
                    createDto.getContactInformationDto().getPhoneNumber());

            // Map to entity
            Account account = accountMapper.toEntity(createDto);
            account.setId(createDto.getId());

            // Set audit fields (if not handled by auditing)
            account.setCreatedAt(LocalDateTime.now());
            account.setUpdatedAt(LocalDateTime.now());

            // Save account
            Account savedAccount = accountRepository.save(account);
            log.info("Account created successfully with ID: {}", savedAccount.getId());

            return accountMapper.toResponseDto(savedAccount);

        } catch (ValidationException | DuplicateEmailException | DuplicatePhoneException e) {
            log.error("Validation failed during account creation: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error during account creation: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create account due to unexpected error", e);
        }
    }

    
    @Transactional(readOnly = true)
    public AccountResponseDto getAccountById(UUID id) {
        try {
            log.debug("Fetching account with ID: {}", id);

            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

            return accountMapper.toResponseDto(account);

        } catch (AccountNotFoundException e) {
            log.warn("Account not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error fetching account with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch account", e);
        }
    }


    @Transactional(readOnly = true)
    public AccountPage getAllAccounts(Pageable pageable) {
        try {
            log.debug("Fetching all active accounts with pagination: {}", pageable);

            // Only fetch non-deleted accounts by default
            Page<Account> accountPage = accountRepository.findByStatusNot(AccountStatus.DELETED, pageable);
            Page<AccountListDto> listDtoPage = accountPage.map(accountMapper::toListDto);

            return new AccountPage(listDtoPage.getContent(), pageable, listDtoPage.getTotalElements());

        } catch (Exception e) {
            log.error("Error fetching accounts: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch accounts", e);
        }
    }

    // Add method to get deleted accounts (for admin purposes)
    @Transactional(readOnly = true)
    public AccountPage getDeletedAccounts(Pageable pageable) {
        try {
            log.debug("Fetching deleted accounts with pagination: {}", pageable);
            log.debug("Fetching deleted accounts with pagination: {}", pageable);

            Page<Account> accountPage = accountRepository.findByStatus(AccountStatus.DELETED, pageable);
            Page<AccountListDto> listDtoPage = accountPage.map(accountMapper::toListDto);

            return new AccountPage(listDtoPage.getContent(), pageable, listDtoPage.getTotalElements());

        } catch (Exception e) {
            log.error("Error fetching deleted accounts: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch deleted accounts", e);
        }
    }

    
    @Transactional(readOnly = true)
    public AccountPage searchAccounts(String query, Pageable pageable) {
        try {
            log.debug("Searching accounts with query: '{}'", query);

            if (query == null || query.trim().isEmpty()) {
                return getAllAccounts(pageable);
            }

            Page<Account> accountPage = accountRepository.searchAccounts(query, pageable);
            Page<AccountListDto> listDtoPage = accountPage.map(accountMapper::toListDto);

            return new AccountPage(listDtoPage.getContent(), pageable, listDtoPage.getTotalElements());

        } catch (Exception e) {
            log.error("Error searching accounts with query '{}': {}", query, e.getMessage(), e);
            throw new RuntimeException("Failed to search accounts", e);
        }
    }

    
    @Transactional
    public AccountResponseDto updateAccount(UUID id, AccountUpdateDto updateDto) {
        try {
            log.info("Updating account with ID: {}", id);

            // Validate input
            validationService.validateAccountUpdateDto(updateDto);

            // Find existing account
            Account existingAccount = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

            // Check if email/phone changes and validate uniqueness
            validateUpdateUniqueness(existingAccount, updateDto);

            // Update account
            accountMapper.updateAccountFromDto(updateDto, existingAccount);
            existingAccount.setUpdatedAt(LocalDateTime.now());

            Account updatedAccount = accountRepository.save(existingAccount);
            log.info("Account updated successfully with ID: {}", id);

            return accountMapper.toResponseDto(updatedAccount);

        } catch (AccountNotFoundException | ValidationException |
                 DuplicateEmailException | DuplicatePhoneException e) {
            log.error("Update failed for account ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error updating account ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to update account", e);
        }
    }


    @Transactional
    public void deleteAccount(UUID id) {
        try {
            log.info("Soft deleting account with ID: {}", id);

            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

            // Add any business rules before deletion
            validateDeletionRules(id);

            // Soft delete by setting status to DELETED
            account.setStatus(AccountStatus.DELETED);
            account.setUpdatedAt(LocalDateTime.now());

            accountRepository.save(account);
            log.info("Account soft deleted successfully with ID: {}", id);

        } catch (AccountNotFoundException | AccountDeletionException e) {
            log.error("Deletion failed for account ID {}: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error deleting account ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete account", e);
        }
    }

    
    @Transactional(readOnly = true)
    public boolean isEmailUnique(String email) {
        try {
            // Only check against non-deleted accounts
            return !accountRepository.existsByContactInformationEmailAndStatusNot(email, AccountStatus.DELETED);
        } catch (Exception e) {
            log.error("Error checking email uniqueness: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to check email uniqueness", e);
        }
    }

    
    @Transactional(readOnly = true)
    public boolean isPhoneUnique(String phoneNumber) {
        try {
            // Only check against non-deleted accounts
            return !accountRepository.existsByContactInformationPhoneNumberAndStatusNot(phoneNumber, AccountStatus.DELETED);
        } catch (Exception e) {
            log.error("Error checking phone uniqueness: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to check phone uniqueness", e);
        }
    }

    
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        try {
            return accountRepository.existsById(id);
        } catch (Exception e) {
            log.error("Error checking account existence for ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to check account existence", e);
        }
    }

    @Transactional
    public AccountResponseDto disableAccount(UUID id) {
        return updateAccountStatus(id, AccountStatus.INACTIVE, "disable");
    }

    @Transactional
    public AccountResponseDto enableAccount(UUID id) {
        return updateAccountStatus(id, AccountStatus.ACTIVE, "enable");
    }

    @Transactional
    public AccountResponseDto suspendAccount(UUID id) {
        return updateAccountStatus(id, AccountStatus.SUSPENDED, "suspend");
    }

    @Transactional
    public AccountResponseDto updateAccountStatus(UUID id, AccountStatus status) {
        String action = status.name().toLowerCase();
        return updateAccountStatus(id, status, action);
    }

    @Transactional(readOnly = true)
    public AccountPage getAccountsByStatus(AccountStatus status, Pageable pageable) {
        try {
            log.debug("Fetching accounts with status: {}", status);

            Page<Account> accountPage = accountRepository.findByStatus(status, pageable);
            Page<AccountListDto> listDtoPage = accountPage.map(accountMapper::toListDto);

            return new AccountPage(listDtoPage.getContent(), pageable, listDtoPage.getTotalElements());

        } catch (Exception e) {
            log.error("Error fetching accounts with status {}: {}", status, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch accounts by status", e);
        }
    }

    @Transactional(readOnly = true)
    public boolean isAccountActive(UUID id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

            return account.getStatus() == AccountStatus.ACTIVE;

        } catch (AccountNotFoundException e) {
            log.warn("Account not found with ID: {}", id);
            throw e;
        } catch (Exception e) {
            log.error("Error checking account status for ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to check account status", e);
        }
    }

    // Private helper method for status updates
    private AccountResponseDto updateAccountStatus(UUID id, AccountStatus status, String action) {
        try {
            log.info("Attempting to {} account with ID: {}", action, id);

            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found with ID: " + id));

            // Check if the status change is valid
            validateStatusTransition(account.getStatus(), status, action);

            // Update status
            account.setStatus(status);
            account.setUpdatedAt(LocalDateTime.now());

            Account updatedAccount = accountRepository.save(account);
            log.info("Account {} successfully for ID: {}", action + "d", id);

            return accountMapper.toResponseDto(updatedAccount);

        } catch (AccountNotFoundException e) {
            log.error("Status update failed - account not found with ID {}: {}", id, e.getMessage());
            throw e;
        } catch (IllegalStateException e) {
            log.error("Invalid status transition for account ID {}: {}", id, e.getMessage());
            throw new ValidationException(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during account {} for ID {}: {}", action, id, e.getMessage(), e);
            throw new RuntimeException("Failed to " + action + " account", e);
        }
    }

    // Validate status transitions
    private void validateStatusTransition(AccountStatus currentStatus, AccountStatus newStatus, String action) {
        // Define valid status transitions
        if (currentStatus == newStatus) {
            throw new IllegalStateException("Account is already " + newStatus.name().toLowerCase());
        }

        switch (action) {
            case "disable":
                if (currentStatus != AccountStatus.ACTIVE) {
                    throw new IllegalStateException("Cannot disable account with status: " + currentStatus);
                }
                break;
            case "enable":
                if (currentStatus == AccountStatus.DELETED) {
                    throw new IllegalStateException("Cannot enable a deleted account");
                }
                break;
            case "suspend":
                if (currentStatus != AccountStatus.ACTIVE) {
                    throw new IllegalStateException("Cannot suspend account with status: " + currentStatus);
                }
                break;
        }
    }

    // Private helper methods
    private void validateUniqueConstraints(String email, String phoneNumber) {
        if (!isEmailUnique(email)) {
            throw new DuplicateEmailException("Email already exists: " + email);
        }
        if (!isPhoneUnique(phoneNumber)) {
            throw new DuplicatePhoneException("Phone number already exists: " + phoneNumber);
        }
    }

    private void validateUpdateUniqueness(Account existingAccount, AccountUpdateDto updateDto) {
        String newEmail = updateDto.getContactInformationDto().getEmail();
        String newPhone = updateDto.getContactInformationDto().getPhoneNumber();

        String currentEmail = existingAccount.getContactInformation().getEmail();
        String currentPhone = existingAccount.getContactInformation().getPhoneNumber();

        if (!currentEmail.equals(newEmail) && !isEmailUnique(newEmail)) {
            throw new DuplicateEmailException("Email already exists: " + newEmail);
        }
        if (!currentPhone.equals(newPhone) && !isPhoneUnique(newPhone)) {
            throw new DuplicatePhoneException("Phone number already exists: " + newPhone);
        }
    }

    private void validateDeletionRules(UUID accountId) {
        // Add any business rules that might prevent deletion
        // For example: check if account has active subscriptions, orders, etc.
        // if (hasActiveSubscriptions(accountId)) {
        //     throw new AccountDeletionException("Cannot delete account with active subscriptions");
        // }
    }
}
