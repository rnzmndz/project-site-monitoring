package site.renzoproject.account_service.mapper;

import org.junit.jupiter.api.Test;
import site.renzoproject.account_service.dto.EmergencyContactDto;
import site.renzoproject.account_service.model.EmergencyContact;

import static org.junit.jupiter.api.Assertions.*;

class EmergencyContactMapperTest {

    private final EmergencyContactMapper mapper = new EmergencyContactMapperImpl();

    @Test
    void testToEntity() {
        // Given
        EmergencyContactDto dto = new EmergencyContactDto();
        dto.setFirstName("Jane");
        dto.setLastName("Doe");
        dto.setRelationship("Spouse");
        dto.setPhoneNumber("+1987654321");

        // When
        EmergencyContact entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertEquals(dto.getLastName(), entity.getLastName());
        assertEquals(dto.getRelationship(), entity.getRelationship());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
    }

    @Test
    void testToDto() {
        // Given
        EmergencyContact entity = EmergencyContact.builder()
                .firstName("John")
                .lastName("Smith")
                .relationship("Brother")
                .phoneNumber("+1234567890")
                .build();

        // When
        EmergencyContactDto dto = mapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getFirstName(), dto.getFirstName());
        assertEquals(entity.getLastName(), dto.getLastName());
        assertEquals(entity.getRelationship(), dto.getRelationship());
        assertEquals(entity.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    void testNullHandling() {
        assertNull(mapper.toEntity(null));
        assertNull(mapper.toDto(null));
    }

    @Test
    void testPartialData() {
        // Given
        EmergencyContactDto dto = new EmergencyContactDto();
        dto.setFirstName("Jane");
        dto.setLastName(null); // null field
        dto.setRelationship("Spouse");
        dto.setPhoneNumber("+1987654321");

        // When
        EmergencyContact entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getFirstName(), entity.getFirstName());
        assertNull(entity.getLastName());
        assertEquals(dto.getRelationship(), entity.getRelationship());
        assertEquals(dto.getPhoneNumber(), entity.getPhoneNumber());
    }
}