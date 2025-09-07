package site.renzoproject.account_service.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.renzoproject.account_service.dto.AccountCreateDto;
import site.renzoproject.account_service.dto.AccountUpdateDto;
import site.renzoproject.account_service.exception.ValidationException;
import site.renzoproject.account_service.model.AccountStatus;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final Validator validator;

    public void validateAccountCreateDto(AccountCreateDto createDto) {
        Set<ConstraintViolation<AccountCreateDto>> violations = validator.validate(createDto);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("; "));
            throw new ValidationException("Validation failed: " + errorMessage);
        }
    }

    public void validateAccountUpdateDto(AccountUpdateDto updateDto) {
        Set<ConstraintViolation<AccountUpdateDto>> violations = validator.validate(updateDto);
        if (!violations.isEmpty()) {
            String errorMessage = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining("; "));
            throw new ValidationException("Validation failed: " + errorMessage);
        }
    }

    public void validateStatusTransition(AccountStatus currentStatus, AccountStatus newStatus) {
        if (currentStatus == AccountStatus.DELETED && newStatus != AccountStatus.DELETED) {
            throw new ValidationException("Cannot reactivate a deleted account");
        }

        if (newStatus == AccountStatus.DELETED && currentStatus == AccountStatus.ACTIVE) {
            // Additional validation for deleting active accounts
            // throw new ValidationException("Cannot directly delete active accounts. Disable first.");
        }
    }
}
