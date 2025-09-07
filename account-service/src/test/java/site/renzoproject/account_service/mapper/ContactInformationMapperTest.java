package site.renzoproject.account_service.mapper;

import org.junit.jupiter.api.Test;
import site.renzoproject.account_service.dto.ContactInformationDto;
import site.renzoproject.account_service.model.ContactInformation;

import static org.junit.jupiter.api.Assertions.*;

class ContactInformationMapperTest {

    private final ContactInformationMapper mapper = new ContactInformationMapperImpl();

    @Test
    void testToEntity() {
        // Given
        ContactInformationDto dto = ContactInformationDto.builder()
                .phoneNumber("+1234567890")
                .email("john.doe@example.com")
                .build();

        // When
        ContactInformation entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(dto.getEmail(), entity.getEmail());
    }

    @Test
    void testToDto() {
        // Given
        ContactInformation entity = ContactInformation.builder()
                .phoneNumber("+1987654321")
                .email("jane.smith@example.com")
                .build();

        // When
        ContactInformationDto dto = mapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
        assertEquals(entity.getEmail(), dto.getEmail());
    }

    @Test
    void testNullHandling() {
        assertNull(mapper.toEntity(null));
        assertNull(mapper.toDto(null));
    }

    @Test
    void testPartialData() {
        // Given
        ContactInformationDto dto = ContactInformationDto.builder()
                .phoneNumber("+1234567890")
                .email(null) // null field
                .build();

        // When
        ContactInformation entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
        assertNull(entity.getEmail());
    }
}