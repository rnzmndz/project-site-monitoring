package site.renzoproject.account_service.mapper;

import org.junit.jupiter.api.Test;
import site.renzoproject.account_service.dto.AddressDto;
import site.renzoproject.account_service.model.Address;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {

    private final AddressMapper addressMapper = new AddressMapperImpl();

    @Test
    void testToEntity() {
        // Given
        AddressDto dto = AddressDto.builder()
                .street("123 Main St")
                .city("New York")
                .state("NY")
                .zipCode("10001")
                .build();

        // When
        Address entity = addressMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getStreet(), entity.getStreet());
        assertEquals(dto.getCity(), entity.getCity());
        assertEquals(dto.getState(), entity.getState());
        assertEquals(dto.getZipCode(), entity.getZipCode());
    }

    @Test
    void testToDto() {
        // Given
        Address entity = Address.builder()
                .street("456 Oak Ave")
                .city("Los Angeles")
                .state("CA")
                .zipCode("90210")
                .build();

        // When
        AddressDto dto = addressMapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals(entity.getStreet(), dto.getStreet());
        assertEquals(entity.getCity(), dto.getCity());
        assertEquals(entity.getState(), dto.getState());
        assertEquals(entity.getZipCode(), dto.getZipCode());
    }

    @Test
    void testNullHandling() {
        assertNull(addressMapper.toEntity(null));
        assertNull(addressMapper.toDto(null));
    }

    @Test
    void testPartialData() {
        // Given
        AddressDto dto = AddressDto.builder()
                .street("789 Pine Rd")
                .city("Chicago")
                .state(null) // null field
                .zipCode("60601")
                .build();

        // When
        Address entity = addressMapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals(dto.getStreet(), entity.getStreet());
        assertEquals(dto.getCity(), entity.getCity());
        assertNull(entity.getState());
        assertEquals(dto.getZipCode(), entity.getZipCode());
    }
}