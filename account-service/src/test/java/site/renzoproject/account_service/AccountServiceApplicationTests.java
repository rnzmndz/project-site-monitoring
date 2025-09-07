package site.renzoproject.account_service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import site.renzoproject.account_service.dto.*;
import site.renzoproject.account_service.exception.AccountNotFoundException;
import site.renzoproject.account_service.exception.DuplicateEmailException;
import site.renzoproject.account_service.exception.ValidationException;
import site.renzoproject.account_service.mapper.AccountMapper;
import site.renzoproject.account_service.model.Account;
import site.renzoproject.account_service.model.AccountStatus;
import site.renzoproject.account_service.model.ContactInformation;
import site.renzoproject.account_service.repository.AccountRepository;
import site.renzoproject.account_service.service.AccountService;
import site.renzoproject.account_service.service.ValidationService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class AccountServiceApplicationTests {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private AccountMapper accountMapper;

	@Mock
	private ValidationService validationService;

	@InjectMocks
	private AccountService accountService;

	@Test
	void createAccount_ValidInput_ReturnsAccountResponse() {
		// Given
		AccountCreateDto createDto = createSampleAccountCreateDto();
		Account account = createSampleAccount();
		AccountResponseDto expectedResponse = createSampleAccountResponseDto();

		doNothing().when(validationService).validateAccountCreateDto(createDto);
		when(accountMapper.toEntity(createDto)).thenReturn(account);
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		when(accountMapper.toResponseDto(account)).thenReturn(expectedResponse);

		// When
		AccountResponseDto result = accountService.createAccount(createDto);

		// Then
		assertNotNull(result);
		assertEquals(expectedResponse.getId(), result.getId());
		verify(validationService).validateAccountCreateDto(createDto);
		verify(accountRepository).save(any(Account.class));
	}

	@Test
	void createAccount_DuplicateEmail_ThrowsDuplicateEmailException() {
		// Given
		AccountCreateDto createDto = createSampleAccountCreateDto();

		doThrow(new DuplicateEmailException("Email already exists"))
				.when(validationService).validateAccountCreateDto(createDto);

		// When & Then
		assertThrows(DuplicateEmailException.class, () -> accountService.createAccount(createDto));
		verify(accountRepository, never()).save(any(Account.class));
	}

	@Test
	void getAccountById_ExistingId_ReturnsAccountResponse() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		AccountResponseDto expectedResponse = createSampleAccountResponseDto();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountMapper.toResponseDto(account)).thenReturn(expectedResponse);

		// When
		AccountResponseDto result = accountService.getAccountById(accountId);

		// Then
		assertNotNull(result);
		assertEquals(expectedResponse.getId(), result.getId());
		verify(accountRepository).findById(accountId);
	}

	@Test
	void getAccountById_NonExistingId_ThrowsAccountNotFoundException() {
		// Given
		UUID accountId = UUID.randomUUID();
		when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(AccountNotFoundException.class, () -> accountService.getAccountById(accountId));
		verify(accountRepository).findById(accountId);
	}

	@Test
	void getAllAccounts_ReturnsPaginatedResults() {
		// Given
		Pageable pageable = PageRequest.of(0, 10, Sort.by("firstName"));
		Page<Account> accountPage = new PageImpl<>(List.of(createSampleAccount()));
		Page<AccountListDto> listDtoPage = new PageImpl<>(List.of(createSampleAccountListDto()));

		when(accountRepository.findByStatusNot(AccountStatus.DELETED, pageable)).thenReturn(accountPage);
		when(accountMapper.toListDto(any(Account.class))).thenReturn(createSampleAccountListDto());

		// When
		AccountPage result = accountService.getAllAccounts(pageable);

		// Then
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		verify(accountRepository).findByStatusNot(AccountStatus.DELETED, pageable);
	}

	@Test
	void getDeletedAccounts_ReturnsDeletedAccounts() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);
		Page<Account> accountPage = new PageImpl<>(List.of(createSampleAccount()));
		Page<AccountListDto> listDtoPage = new PageImpl<>(List.of(createSampleAccountListDto()));

		when(accountRepository.findByStatus(AccountStatus.DELETED, pageable)).thenReturn(accountPage);
		when(accountMapper.toListDto(any(Account.class))).thenReturn(createSampleAccountListDto());

		// When
		AccountPage result = accountService.getDeletedAccounts(pageable);

		// Then
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		verify(accountRepository).findByStatus(AccountStatus.DELETED, pageable);
	}

	@Test
	void updateAccount_ValidInput_ReturnsUpdatedAccount() {
		// Given
		UUID accountId = UUID.randomUUID();
		AccountUpdateDto updateDto = createSampleAccountUpdateDto();
		Account existingAccount = createSampleAccount();
		Account updatedAccount = createSampleAccount();
		AccountResponseDto expectedResponse = createSampleAccountResponseDto();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
		when(accountRepository.save(any(Account.class))).thenReturn(updatedAccount);
		when(accountMapper.toResponseDto(updatedAccount)).thenReturn(expectedResponse);

		// When
		AccountResponseDto result = accountService.updateAccount(accountId, updateDto);

		// Then
		assertNotNull(result);
		assertEquals(expectedResponse.getId(), result.getId());
		verify(accountRepository).findById(accountId);
		verify(accountRepository).save(any(Account.class));
	}

	@Test
	void deleteAccount_ExistingId_SoftDeletesAccount() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.ACTIVE);

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);

		// When
		accountService.deleteAccount(accountId);

		// Then
		verify(accountRepository).findById(accountId);
		verify(accountRepository).save(any(Account.class));
		assertEquals(AccountStatus.DELETED, account.getStatus());
	}

	@Test
	void disableAccount_ActiveAccount_ReturnsDisabledAccount() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.ACTIVE);
		AccountResponseDto expectedResponse = createSampleAccountResponseDto();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		when(accountMapper.toResponseDto(account)).thenReturn(expectedResponse);

		// When
		AccountResponseDto result = accountService.disableAccount(accountId);

		// Then
		assertNotNull(result);
		assertEquals(AccountStatus.INACTIVE, account.getStatus());
		verify(accountRepository).findById(accountId);
		verify(accountRepository).save(account);
	}

	@Test
	void enableAccount_InactiveAccount_ReturnsEnabledAccount() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.INACTIVE);
		AccountResponseDto expectedResponse = createSampleAccountResponseDto();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		when(accountMapper.toResponseDto(account)).thenReturn(expectedResponse);

		// When
		AccountResponseDto result = accountService.enableAccount(accountId);

		// Then
		assertNotNull(result);
		assertEquals(AccountStatus.ACTIVE, account.getStatus());
		verify(accountRepository).findById(accountId);
		verify(accountRepository).save(account);
	}

	@Test
	void suspendAccount_ActiveAccount_ReturnsSuspendedAccount() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.ACTIVE);
		AccountResponseDto expectedResponse = createSampleAccountResponseDto();

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		when(accountMapper.toResponseDto(account)).thenReturn(expectedResponse);

		// When
		AccountResponseDto result = accountService.suspendAccount(accountId);

		// Then
		assertNotNull(result);
		assertEquals(AccountStatus.SUSPENDED, account.getStatus());
		verify(accountRepository).findById(accountId);
		verify(accountRepository).save(account);
	}

	@Test
	void isEmailUnique_UniqueEmail_ReturnsTrue() {
		// Given
		String email = "unique@example.com";
		when(accountRepository.existsByContactInformationEmailAndStatusNot(email, AccountStatus.DELETED))
				.thenReturn(false);

		// When
		boolean result = accountService.isEmailUnique(email);

		// Then
		assertTrue(result);
		verify(accountRepository).existsByContactInformationEmailAndStatusNot(email, AccountStatus.DELETED);
	}

	@Test
	void isPhoneUnique_UniquePhone_ReturnsTrue() {
		// Given
		String phone = "+1234567890";
		when(accountRepository.existsByContactInformationPhoneNumberAndStatusNot(phone, AccountStatus.DELETED))
				.thenReturn(false);

		// When
		boolean result = accountService.isPhoneUnique(phone);

		// Then
		assertTrue(result);
		verify(accountRepository).existsByContactInformationPhoneNumberAndStatusNot(phone, AccountStatus.DELETED);
	}

	@Test
	void existsById_ExistingId_ReturnsTrue() {
		// Given
		UUID accountId = UUID.randomUUID();
		when(accountRepository.existsById(accountId)).thenReturn(true);

		// When
		boolean result = accountService.existsById(accountId);

		// Then
		assertTrue(result);
		verify(accountRepository).existsById(accountId);
	}

	@Test
	void isAccountActive_ActiveAccount_ReturnsTrue() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.ACTIVE);

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

		// When
		boolean result = accountService.isAccountActive(accountId);

		// Then
		assertTrue(result);
		verify(accountRepository).findById(accountId);
	}

	@Test
	void getAccountsByStatus_ReturnsFilteredAccounts() {
		// Given
		Pageable pageable = PageRequest.of(0, 10);
		AccountStatus status = AccountStatus.ACTIVE;
		Page<Account> accountPage = new PageImpl<>(List.of(createSampleAccount()));
		Page<AccountListDto> listDtoPage = new PageImpl<>(List.of(createSampleAccountListDto()));

		when(accountRepository.findByStatus(status, pageable)).thenReturn(accountPage);
		when(accountMapper.toListDto(any(Account.class))).thenReturn(createSampleAccountListDto());

		// When
		AccountPage result = accountService.getAccountsByStatus(status, pageable);

		// Then
		assertNotNull(result);
		assertEquals(1, result.getContent().size());
		verify(accountRepository).findByStatus(status, pageable);
	}

	@Test
	void disableAccount_AlreadyInactive_ThrowsValidationException() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.INACTIVE);

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

		// When & Then
		assertThrows(ValidationException.class, () -> accountService.disableAccount(accountId));
		verify(accountRepository, never()).save(any(Account.class));
	}

	@Test
	void enableAccount_DeletedAccount_ThrowsValidationException() {
		// Given
		UUID accountId = UUID.randomUUID();
		Account account = createSampleAccount();
		account.setStatus(AccountStatus.DELETED);

		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

		// When & Then
		assertThrows(ValidationException.class, () -> accountService.enableAccount(accountId));
		verify(accountRepository, never()).save(any(Account.class));
	}

	// Helper methods to create test data
	private AccountCreateDto createSampleAccountCreateDto() {
		return AccountCreateDto.builder()
				.firstName("John")
				.middleName("Michael")
				.lastName("Doe")
				.nameSuffix("Jr.")
				.gender("Male")
				.birthDate(LocalDate.of(1990, 5, 20))
				.imageUrl("https://example.com/profile.jpg")
				.addressDto(createSampleAddressDto())
				.contactInformationDto(createSampleContactInformationDto())
				.emergencyContactDto(createSampleEmergencyContactDto())
				.build();
	}

	private AccountUpdateDto createSampleAccountUpdateDto() {
		return AccountUpdateDto.builder()
				.firstName("Jane")
				.middleName("Anne")
				.lastName("Smith")
				.nameSuffix("Sr.")
				.gender("Female")
				.birthDate(LocalDate.of(1985, 8, 15))
				.imageUrl("https://example.com/new-profile.jpg")
				.addressDto(createSampleAddressDto())
				.contactInformationDto(createSampleContactInformationDto())
				.emergencyContactDto(createSampleEmergencyContactDto())
				.build();
	}

	private Account createSampleAccount() {
		return Account.builder()
				.id(UUID.randomUUID())
				.firstName("John")
				.middleName("Michael")
				.lastName("Doe")
				.nameSuffix("Jr.")
				.gender("Male")
				.birthDate(LocalDate.of(1990, 5, 20))
				.imageUrl("https://example.com/profile.jpg")
				.status(AccountStatus.ACTIVE)
				.contactInformation(ContactInformation.builder()
						.phoneNumber("+1234567890")
						.email("john.doe@example.com")
						.build())
				.createdAt(LocalDateTime.now().minusDays(1))
				.updatedAt(LocalDateTime.now())
				.createdBy("admin@company.com")
				.modifiedBy("hr@company.com")
				.build();
	}

	private AccountResponseDto createSampleAccountResponseDto() {
		return AccountResponseDto.builder()
				.id(UUID.randomUUID())
				.firstName("John")
				.middleName("Michael")
				.lastName("Doe")
				.nameSuffix("Jr.")
				.gender("Male")
				.birthDate(LocalDate.of(1990, 5, 20))
				.imageUrl("https://example.com/profile.jpg")
				.status(AccountStatus.ACTIVE)
				.addressDto(createSampleAddressDto())
				.contactInformationDto(createSampleContactInformationDto())
				.emergencyContactDto(createSampleEmergencyContactDto())
				.createdAt(LocalDateTime.now().minusDays(1))
				.updatedAt(LocalDateTime.now())
				.createdBy("admin@company.com")
				.modifiedBy("hr@company.com")
				.build();
	}

	private AccountListDto createSampleAccountListDto() {
		return AccountListDto.builder()
				.id(UUID.randomUUID())
				.firstName("John")
				.middleName("Michael")
				.lastName("Doe")
				.imageUrl("https://example.com/profile.jpg")
				.build();
	}

	private AddressDto createSampleAddressDto() {
		return AddressDto.builder()
				.street("123 Main St")
				.city("New York")
				.state("NY")
				.zipCode("10001")
				.build();
	}

	private ContactInformationDto createSampleContactInformationDto() {
		return ContactInformationDto.builder()
				.phoneNumber("+1234567890")
				.email("john.doe@example.com")
				.build();
	}

	private EmergencyContactDto createSampleEmergencyContactDto() {
		EmergencyContactDto dto = new EmergencyContactDto();
		dto.setFirstName("Jane");
		dto.setLastName("Doe");
		dto.setRelationship("Spouse");
		dto.setPhoneNumber("+1987654321");
		return dto;
	}
}

