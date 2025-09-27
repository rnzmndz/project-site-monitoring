package site.renzoproject.employee_service.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.EmployeeRequestDto;
import site.renzoproject.employee_service.dto.EmployeeResponseDto;
import site.renzoproject.employee_service.model.Employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = new EmployeeMapperImpl();

    @Test
    void testToEntity_FromEmployeeRequestDto() {
        // Given
        UUID accountId = UUID.randomUUID();
        LocalDate hiredDate = LocalDate.of(2023, 1, 15);

        EmployeeRequestDto requestDto = EmployeeRequestDto.builder()
                .accountId(accountId)
                .firstName("John")
                .middleName("Robert")
                .lastName("Doe")
                .nameSuffix("Jr.")
                .jobTitle("Software Engineer")
                .department("IT")
                .hiredDate(hiredDate)
                .build();

        // When
        Employee employee = employeeMapper.toEntity(requestDto);

        // Then
        assertNotNull(employee);
        assertNull(employee.getId()); // ID should be ignored
        assertEquals(accountId, employee.getAccountId());
        assertEquals("John", employee.getFirstName());
        assertEquals("Robert", employee.getMiddleName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("Jr.", employee.getNameSuffix());
        assertEquals("Software Engineer", employee.getJobTitle());
        assertEquals("IT", employee.getDepartment());
        assertEquals(hiredDate, employee.getHiredDate());

        // Relationships and audit fields should be ignored
        assertNull(employee.getScheduleAssignments());
        assertNull(employee.getLeaves());
        assertNull(employee.getAttendances());
        assertNull(employee.getCreatedAt());
        assertNull(employee.getUpdatedAt());
        assertNull(employee.getCreatedBy());
        assertNull(employee.getModifiedBy());
    }

    @Test
    void testToEntity_WithNullValues() {
        // Given
        EmployeeRequestDto requestDto = EmployeeRequestDto.builder()
                .accountId(null)
                .firstName(null)
                .middleName(null)
                .lastName(null)
                .nameSuffix(null)
                .jobTitle(null)
                .department(null)
                .hiredDate(null)
                .build();

        // When
        Employee employee = employeeMapper.toEntity(requestDto);

        // Then
        assertNotNull(employee);
        assertNull(employee.getAccountId());
        assertNull(employee.getFirstName());
        assertNull(employee.getMiddleName());
        assertNull(employee.getLastName());
        assertNull(employee.getNameSuffix());
        assertNull(employee.getJobTitle());
        assertNull(employee.getDepartment());
        assertNull(employee.getHiredDate());
    }

    @Test
    void testToEntity_WithEmptyStrings() {
        // Given
        EmployeeRequestDto requestDto = EmployeeRequestDto.builder()
                .firstName("John")
                .middleName("") // Empty string
                .lastName("Doe")
                .nameSuffix("") // Empty string
                .jobTitle("Developer")
                .department("IT")
                .build();

        // When
        Employee employee = employeeMapper.toEntity(requestDto);

        // Then
        assertNotNull(employee);
        assertEquals("John", employee.getFirstName());
        assertEquals("", employee.getMiddleName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("", employee.getNameSuffix());
        assertEquals("Developer", employee.getJobTitle());
        assertEquals("IT", employee.getDepartment());
    }

    @Test
    void testToResponseDto_FromEmployee() {
        // Given
        UUID employeeId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();
        LocalDate hiredDate = LocalDate.of(2023, 1, 15);
        LocalDateTime createdAt = LocalDateTime.now().minusDays(30);
        LocalDateTime updatedAt = LocalDateTime.now();

        Employee employee = Employee.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("Jane")
                .middleName("Marie")
                .lastName("Smith")
                .nameSuffix("PhD")
                .jobTitle("Senior Developer")
                .department("Engineering")
                .hiredDate(hiredDate)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy("system")
                .modifiedBy("admin")
                .build();

        // When
        EmployeeResponseDto responseDto = employeeMapper.toResponseDto(employee);

        // Then
        assertNotNull(responseDto);
        assertEquals(employeeId, responseDto.getId());
        assertEquals(accountId, responseDto.getAccountId());
        assertEquals("Jane", responseDto.getFirstName());
        assertEquals("Marie", responseDto.getMiddleName());
        assertEquals("Smith", responseDto.getLastName());
        assertEquals("PhD", responseDto.getNameSuffix());
        assertEquals("Senior Developer", responseDto.getJobTitle());
        assertEquals("Engineering", responseDto.getDepartment());
        assertEquals(hiredDate, responseDto.getHiredDate());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(updatedAt, responseDto.getUpdatedAt());
        assertEquals("system", responseDto.getCreatedBy());
        assertEquals("admin", responseDto.getModifiedBy());
    }

    @Test
    void testToResponseDto_WithPartialData() {
        // Given
        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .firstName("Alice")
                .lastName("Johnson")
                .jobTitle("Manager")
                .department("HR")
                // Other fields are null
                .build();

        // When
        EmployeeResponseDto responseDto = employeeMapper.toResponseDto(employee);

        // Then
        assertNotNull(responseDto);
        assertEquals("Alice", responseDto.getFirstName());
        assertEquals("Johnson", responseDto.getLastName());
        assertEquals("Manager", responseDto.getJobTitle());
        assertEquals("HR", responseDto.getDepartment());
        assertNull(responseDto.getMiddleName());
        assertNull(responseDto.getNameSuffix());
        assertNull(responseDto.getAccountId());
        assertNull(responseDto.getHiredDate());
        assertNull(responseDto.getCreatedAt());
        assertNull(responseDto.getUpdatedAt());
    }

    @Test
    void testUpdateEntityFromDto() {
        // Given
        UUID existingEmployeeId = UUID.randomUUID();
        LocalDateTime existingCreatedAt = LocalDateTime.now().minusDays(30);

        Employee existingEmployee = Employee.builder()
                .id(existingEmployeeId)
                .accountId(UUID.randomUUID())
                .firstName("OldFirstName")
                .lastName("OldLastName")
                .jobTitle("OldJobTitle")
                .department("OldDepartment")
                .hiredDate(LocalDate.of(2020, 1, 1))
                .createdAt(existingCreatedAt)
                .createdBy("oldUser")
                .build();

        UUID newAccountId = UUID.randomUUID();
        LocalDate newHiredDate = LocalDate.of(2024, 6, 1);

        EmployeeRequestDto updateDto = EmployeeRequestDto.builder()
                .accountId(newAccountId)
                .firstName("NewFirstName")
                .middleName("NewMiddleName")
                .lastName("NewLastName")
                .nameSuffix("Jr.")
                .jobTitle("NewJobTitle")
                .department("NewDepartment")
                .hiredDate(newHiredDate)
                .build();

        // When
        employeeMapper.updateEntityFromDto(updateDto, existingEmployee);

        // Then
        // ID and audit fields should remain unchanged
        assertEquals(existingEmployeeId, existingEmployee.getId());
        assertEquals(existingCreatedAt, existingEmployee.getCreatedAt());
        assertEquals("oldUser", existingEmployee.getCreatedBy());

        // Other fields should be updated
        assertEquals(newAccountId, existingEmployee.getAccountId());
        assertEquals("NewFirstName", existingEmployee.getFirstName());
        assertEquals("NewMiddleName", existingEmployee.getMiddleName());
        assertEquals("NewLastName", existingEmployee.getLastName());
        assertEquals("Jr.", existingEmployee.getNameSuffix());
        assertEquals("NewJobTitle", existingEmployee.getJobTitle());
        assertEquals("NewDepartment", existingEmployee.getDepartment());
        assertEquals(newHiredDate, existingEmployee.getHiredDate());

        // Relationships should remain null (ignored during update)
        assertNull(existingEmployee.getScheduleAssignments());
        assertNull(existingEmployee.getLeaves());
        assertNull(existingEmployee.getAttendances());
    }

    @Test
    void testUpdateEntityFromDto_WithNullValues() {
        // Given
        Employee existingEmployee = Employee.builder()
                .id(UUID.randomUUID())
                .accountId(UUID.randomUUID())
                .firstName("John")
                .middleName("Middle")
                .lastName("Doe")
                .nameSuffix("Sr.")
                .jobTitle("Developer")
                .department("IT")
                .hiredDate(LocalDate.now())
                .build();

        EmployeeRequestDto updateDto = EmployeeRequestDto.builder()
                .accountId(null)
                .firstName(null)
                .middleName(null)
                .lastName(null)
                .nameSuffix(null)
                .jobTitle(null)
                .department(null)
                .hiredDate(null)
                .build();

        // When
        employeeMapper.updateEntityFromDto(updateDto, existingEmployee);

        // Then
        // Fields should be set to null from the DTO
        assertNull(existingEmployee.getAccountId());
        assertNull(existingEmployee.getFirstName());
        assertNull(existingEmployee.getMiddleName());
        assertNull(existingEmployee.getLastName());
        assertNull(existingEmployee.getNameSuffix());
        assertNull(existingEmployee.getJobTitle());
        assertNull(existingEmployee.getDepartment());
        assertNull(existingEmployee.getHiredDate());
    }

    @Test
    void testGetFullName_CompleteName() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .middleName("Robert")
                .lastName("Doe")
                .nameSuffix("Jr.")
                .build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("John Robert Doe Jr.", fullName);
    }

    @Test
    void testGetFullName_OnlyFirstAndLast() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("John Doe", fullName);
    }

    @Test
    void testGetFullName_OnlyFirstName() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("John", fullName);
    }

    @Test
    void testGetFullName_WithEmptyMiddleName() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .middleName("") // Empty string
                .lastName("Doe")
                .build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("John Doe", fullName);
    }

    @Test
    void testGetFullName_WithWhitespaceMiddleName() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .middleName("   ") // Whitespace only
                .lastName("Doe")
                .build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("John Doe", fullName);
    }

    @Test
    void testGetFullName_WithEmptySuffix() {
        // Given
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .nameSuffix("") // Empty string
                .build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("John Doe", fullName);
    }

    @Test
    void testGetFullName_WithNullEmployee() {
        // When
        String fullName = employeeMapper.getFullName(null);

        // Then
        assertNull(fullName);
    }

    @Test
    void testGetFullName_WithAllNullNameParts() {
        // Given
        Employee employee = Employee.builder().build();

        // When
        String fullName = employeeMapper.getFullName(employee);

        // Then
        assertEquals("", fullName);
    }

    @Test
    void testMapAccountIdToEmployee() {
        // Given
        UUID accountId = UUID.randomUUID();

        // When
        Employee employee = employeeMapper.mapAccountIdToEmployee(accountId);

        // Then
        assertNotNull(employee);
        assertEquals(accountId, employee.getAccountId());
        assertNull(employee.getId());
        assertNull(employee.getFirstName());
    }

    @Test
    void testMapAccountIdToEmployee_WithNullId() {
        // When
        Employee employee = employeeMapper.mapAccountIdToEmployee(null);

        // Then
        assertNull(employee);
    }

    @Test
    void testAfterMapping_Callback() {
        // Given
        Employee employee = new Employee();
        EmployeeRequestDto requestDto = EmployeeRequestDto.builder()
                .firstName("Test")
                .lastName("User")
                .build();

        // When - The @AfterMapping method should be called automatically
        employeeMapper.afterMapping(employee, requestDto);

        // Then - The method currently has no implementation, so we just verify it doesn't throw
        assertNotNull(employee);
        // Add assertions here if you add business logic to the afterMapping method
    }

    @Test
    void testAfterMapping_WithNullParameters() {
        // When - Should not throw exception even with null parameters
        employeeMapper.afterMapping(null, null);

        // Then - No exception expected
        assertTrue(true); // Test passes if no exception is thrown
    }

    @Test
    void testEmployeeBuilderPattern() {
        // Given - Test that the builder pattern works correctly
        UUID id = UUID.randomUUID();
        String firstName = "Builder";
        String lastName = "Test";

        // When
        Employee employee = Employee.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        // Then
        assertNotNull(employee);
        assertEquals(id, employee.getId());
        assertEquals(firstName, employee.getFirstName());
        assertEquals(lastName, employee.getLastName());
    }

    @Test
    void testMultipleMappings_Consistency() {
        // Given
        EmployeeRequestDto requestDto = EmployeeRequestDto.builder()
                .firstName("Consistency")
                .lastName("Test")
                .jobTitle("Tester")
                .department("QA")
                .build();

        // When - Map multiple times to ensure consistency
        Employee employee1 = employeeMapper.toEntity(requestDto);
        Employee employee2 = employeeMapper.toEntity(requestDto);

        // Then
        assertNotNull(employee1);
        assertNotNull(employee2);
        assertEquals(employee1.getFirstName(), employee2.getFirstName());
        assertEquals(employee1.getLastName(), employee2.getLastName());
        assertEquals(employee1.getJobTitle(), employee2.getJobTitle());
        assertEquals(employee1.getDepartment(), employee2.getDepartment());

        // They should be different instances
        assertNotSame(employee1, employee2);
    }

    @Test
    void testResponseDto_AllFieldsNull() {
        // Given
        Employee employee = new Employee(); // All fields null

        // When
        EmployeeResponseDto responseDto = employeeMapper.toResponseDto(employee);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertNull(responseDto.getAccountId());
        assertNull(responseDto.getFirstName());
        assertNull(responseDto.getLastName());
        assertNull(responseDto.getJobTitle());
        assertNull(responseDto.getDepartment());
        assertNull(responseDto.getHiredDate());
    }
}