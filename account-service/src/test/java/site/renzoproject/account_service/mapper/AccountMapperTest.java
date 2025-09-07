package site.renzoproject.account_service.mapper;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.account_service.dto.*;
import site.renzoproject.account_service.model.Account;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

    @Spy
    private AddressMapper addressMapper = new AddressMapperImpl();

    @Spy
    private ContactInformationMapper contactInformationMapper = new ContactInformationMapperImpl();

    @Spy
    private EmergencyContactMapper emergencyContactMapper = new EmergencyContactMapperImpl();

    @InjectMocks
    private AccountMapper accountMapper = new AccountMapperImpl();

    @Test
    void testToEntityFromAccountCreateDto() {
        // Given
        AccountCreateDto createDto = createSampleAccountCreateDto();

        // When
        Account account = accountMapper.toEntity(createDto);

        // Then
        assertNotNull(account);
        assertNull(account.getId()); // Should be ignored
        assertEquals("John", account.getFirstName());
        assertEquals("Michael", account.getMiddleName());
        assertEquals("Doe", account.getLastName());
        assertEquals("Jr.", account.getNameSuffix());
        assertEquals("Male", account.getGender());
        assertEquals(LocalDate.of(1990, 5, 20), account.getBirthDate());
        assertEquals("https://example.com/profile.jpg", account.getImageUrl());

        // Verify embedded objects
        assertNotNull(account.getAddress());
        assertNotNull(account.getContactInformation());
        assertNotNull(account.getEmergencyContact());
    }

    @Test
    void testUpdateAccountFromDto() {
        // Given
        Account existingAccount = createSampleAccount();
        AccountUpdateDto updateDto = createSampleAccountUpdateDto();

        // When
        accountMapper.updateAccountFromDto(updateDto, existingAccount);

        // Then
        assertEquals("Jane", existingAccount.getFirstName());
        assertEquals("Anne", existingAccount.getMiddleName());
        assertEquals("Smith", existingAccount.getLastName());
        assertEquals("Sr.", existingAccount.getNameSuffix());
        assertEquals("Female", existingAccount.getGender());
        assertEquals(LocalDate.of(1985, 8, 15), existingAccount.getBirthDate());
        assertEquals("https://example.com/new-profile.jpg", existingAccount.getImageUrl());

        // Verify embedded objects are updated
        assertNotNull(existingAccount.getAddress());
        assertNotNull(existingAccount.getContactInformation());
        assertNotNull(existingAccount.getEmergencyContact());
    }

    @Test
    void testToResponseDto() {
        // Given
        Account account = createSampleAccount();

        // When
        AccountResponseDto responseDto = accountMapper.toResponseDto(account);

        // Then
        assertNotNull(responseDto);
        assertEquals(account.getId(), responseDto.getId());
        assertEquals(account.getFirstName(), responseDto.getFirstName());
        assertEquals(account.getMiddleName(), responseDto.getMiddleName());
        assertEquals(account.getLastName(), responseDto.getLastName());
        assertEquals(account.getNameSuffix(), responseDto.getNameSuffix());
        assertEquals(account.getGender(), responseDto.getGender());
        assertEquals(account.getBirthDate(), responseDto.getBirthDate());
        assertEquals(account.getImageUrl(), responseDto.getImageUrl());
        assertEquals(account.getCreatedAt(), responseDto.getCreatedAt());
        assertEquals(account.getUpdatedAt(), responseDto.getUpdatedAt());
        assertEquals(account.getCreatedBy(), responseDto.getCreatedBy());
        assertEquals(account.getModifiedBy(), responseDto.getModifiedBy());

        // Verify embedded DTOs
        assertNotNull(responseDto.getAddressDto());
        assertNotNull(responseDto.getContactInformationDto());
        assertNotNull(responseDto.getEmergencyContactDto());
    }

    @Test
    void testToListDto() {
        // Given
        Account account = createSampleAccount();

        // When
        AccountListDto listDto = accountMapper.toListDto(account);

        // Then
        assertNotNull(listDto);
        assertEquals(account.getId(), listDto.getId());
        assertEquals(account.getFirstName(), listDto.getFirstName());
        assertEquals(account.getMiddleName(), listDto.getMiddleName());
        assertEquals(account.getLastName(), listDto.getLastName());
        assertEquals(account.getImageUrl(), listDto.getImageUrl());
    }

    @Test
    void testNullHandling() {
        // Test null input
        assertNull(accountMapper.toEntity((AccountCreateDto) null));
        assertNull(accountMapper.toResponseDto(null));
        assertNull(accountMapper.toListDto(null));

        // Test update with null dto should not throw exception
        Account account = new Account();
        accountMapper.updateAccountFromDto(null, account);
        // Should not modify the account
        assertNull(account.getFirstName());
    }

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
                .address(createSampleAddress())
                .contactInformation(createSampleContactInformation())
                .emergencyContact(createSampleEmergencyContact())
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .createdBy("admin@company.com")
                .modifiedBy("hr@company.com")
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

    private site.renzoproject.account_service.model.Address createSampleAddress() {
        return site.renzoproject.account_service.model.Address.builder()
                .street("123 Main St")
                .city("New York")
                .state("NY")
                .zipCode("10001")
                .build();
    }

    private site.renzoproject.account_service.model.ContactInformation createSampleContactInformation() {
        return site.renzoproject.account_service.model.ContactInformation.builder()
                .phoneNumber("+1234567890")
                .email("john.doe@example.com")
                .build();
    }

    private site.renzoproject.account_service.model.EmergencyContact createSampleEmergencyContact() {
        return site.renzoproject.account_service.model.EmergencyContact.builder()
                .firstName("Jane")
                .lastName("Doe")
                .relationship("Spouse")
                .phoneNumber("+1987654321")
                .build();
    }
}