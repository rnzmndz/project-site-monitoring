package site.renzoproject.employee_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import site.renzoproject.employee_service.client.AccountClient;
import site.renzoproject.employee_service.dto.EmployeePage;
import site.renzoproject.employee_service.dto.EmployeeRequestDto;
import site.renzoproject.employee_service.dto.EmployeeResponseDto;
import site.renzoproject.employee_service.dto.account.AccountResponseDto;
import site.renzoproject.employee_service.exception.DuplicateEmployeeException;
import site.renzoproject.employee_service.exception.EmployeeNotFoundException;
import site.renzoproject.employee_service.mapper.EmployeeMapper;
import site.renzoproject.employee_service.model.Employee;
import site.renzoproject.employee_service.repository.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private AccountClient accountClient;

    @InjectMocks
    private EmployeeService employeeService;

    private UUID employeeId;
    private UUID accountId;
    private Employee employee;
    private EmployeeRequestDto employeeRequestDto;
    private EmployeeResponseDto employeeResponseDto;
    private AccountResponseDto accountResponseDto;

    @BeforeEach
    void setUp() {
        employeeId = UUID.randomUUID();
        accountId = UUID.randomUUID();

        employee = Employee.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("John")
                .lastName("Doe")
                .middleName("Michael")
                .nameSuffix("Jr.")
                .jobTitle("Software Engineer")
                .department("IT")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .modifiedBy("system")
                .build();

        employeeRequestDto = EmployeeRequestDto.builder()
                .accountId(accountId)
                .firstName("John")
                .lastName("Doe")
                .middleName("Michael")
                .nameSuffix("Jr.")
                .jobTitle("Software Engineer")
                .department("IT")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .build();

        employeeResponseDto = EmployeeResponseDto.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("John")
                .lastName("Doe")
                .middleName("Michael")
                .nameSuffix("Jr.")
                .jobTitle("Software Engineer")
                .department("IT")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .modifiedBy("system")
                .build();

        accountResponseDto = AccountResponseDto.builder()
                .id(accountId)
                .firstName("John")
                .lastName("Doe")
                .middleName("Michael")
                .nameSuffix("Jr.")
                .build();
    }

    @Test
    void createEmployee_Success() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        when(accountClient.getAccountById(accountId)).thenReturn(accountResponseDto);
        when(employeeMapper.toEntity(employeeRequestDto)).thenReturn(employee);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        EmployeeResponseDto result = employeeService.createEmployee(employeeRequestDto);

        // Then
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        assertEquals(accountId, result.getAccountId());
        assertEquals("John", result.getFirstName());

        verify(employeeRepository).findByAccountId(accountId);
        verify(accountClient).getAccountById(accountId);
        verify(employeeMapper).toEntity(employeeRequestDto);
        verify(employeeRepository).save(any(Employee.class));
        verify(employeeMapper).toResponseDto(employee);
    }

    @Test
    void createEmployee_WhenEmployeeAlreadyExists_ThrowsException() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.of(employee));

        // When & Then
        assertThrows(DuplicateEmployeeException.class,
                () -> employeeService.createEmployee(employeeRequestDto));

        verify(employeeRepository).findByAccountId(accountId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void createEmployee_WhenAccountValidationFails_ThrowsException() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        when(accountClient.getAccountById(accountId)).thenThrow(new RuntimeException("Account not found"));

        // When & Then
        assertThrows(RuntimeException.class,
                () -> employeeService.createEmployee(employeeRequestDto));

        verify(employeeRepository).findByAccountId(accountId);
        verify(accountClient).getAccountById(accountId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_Success() {
        // Given
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        EmployeeResponseDto result = employeeService.getEmployeeById(employeeId);

        // Then
        assertNotNull(result);
        assertEquals(employeeId, result.getId());
        verify(employeeRepository).findById(employeeId);
        verify(employeeMapper).toResponseDto(employee);
    }

    @Test
    void getEmployeeById_WhenNotFound_ThrowsException() {
        // Given
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(employeeId));

        verify(employeeRepository).findById(employeeId);
        verify(employeeMapper, never()).toResponseDto(any(Employee.class));
    }

    @Test
    void getEmployeeByAccountId_Success() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        EmployeeResponseDto result = employeeService.getEmployeeByAccountId(accountId);

        // Then
        assertNotNull(result);
        assertEquals(accountId, result.getAccountId());
        verify(employeeRepository).findByAccountId(accountId);
        verify(employeeMapper).toResponseDto(employee);
    }

    @Test
    void getEmployeeByAccountId_WhenNotFound_ThrowsException() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeByAccountId(accountId));

        verify(employeeRepository).findByAccountId(accountId);
        verify(employeeMapper, never()).toResponseDto(any(Employee.class));
    }

    @Test
    void getAllEmployees_Success() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        List<Employee> employees = List.of(employee);
        Page<Employee> employeePage = new PageImpl<>(employees, pageable, 1);

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        EmployeePage result = employeeService.getAllEmployees(pageable);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());

        verify(employeeRepository).findAll(pageable);
        verify(employeeMapper).toResponseDto(employee);
    }

    @Test
    void updateEmployee_Success() {
        // Given
        EmployeeRequestDto updateRequest = EmployeeRequestDto.builder()
                .accountId(accountId)
                .firstName("Jane")
                .lastName("Smith")
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("Jane")
                .lastName("Smith")
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .build();

        EmployeeResponseDto updatedResponse = EmployeeResponseDto.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("Jane")
                .lastName("Smith")
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        lenient().when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.empty());
        lenient().when(accountClient.getAccountById(accountId)).thenReturn(accountResponseDto);
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);
        when(employeeMapper.toResponseDto(updatedEmployee)).thenReturn(updatedResponse);

        // When
        EmployeeResponseDto result = employeeService.updateEmployee(employeeId, updateRequest);

        // Then
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("Senior Software Engineer", result.getJobTitle());

        verify(employeeRepository).findById(employeeId);
//        verify(employeeRepository).findByAccountId(accountId);
//        verify(accountClient).getAccountById(accountId);
        verify(employeeMapper).updateEntityFromDto(updateRequest, employee);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toResponseDto(updatedEmployee);
    }

    @Test
    void updateEmployee_WhenAccountIdChangedAndDuplicateExists_ThrowsException() {
        // Given
        UUID newAccountId = UUID.randomUUID();
        EmployeeRequestDto updateRequest = EmployeeRequestDto.builder()
                .accountId(newAccountId)
                .firstName("John")
                .lastName("Doe")
                .build();

        Employee existingEmployeeWithNewAccount = Employee.builder()
                .id(UUID.randomUUID())
                .accountId(newAccountId)
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.findByAccountId(newAccountId)).thenReturn(Optional.of(existingEmployeeWithNewAccount));

        // When & Then
        assertThrows(DuplicateEmployeeException.class,
                () -> employeeService.updateEmployee(employeeId, updateRequest));

        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).findByAccountId(newAccountId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void partialUpdateEmployee_Success() {
        // Given
        EmployeeRequestDto partialUpdateRequest = EmployeeRequestDto.builder()
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .build();

        Employee updatedEmployee = Employee.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("John")
                .lastName("Doe")
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .build();

        EmployeeResponseDto updatedResponse = EmployeeResponseDto.builder()
                .id(employeeId)
                .accountId(accountId)
                .firstName("John")
                .lastName("Doe")
                .jobTitle("Senior Software Engineer")
                .department("Engineering")
                .hiredDate(LocalDate.of(2023, 1, 15))
                .build();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(updatedEmployee);
        when(employeeMapper.toResponseDto(updatedEmployee)).thenReturn(updatedResponse);

        // When
        EmployeeResponseDto result = employeeService.partialUpdateEmployee(employeeId, partialUpdateRequest);

        // Then
        assertNotNull(result);
        assertEquals("Senior Software Engineer", result.getJobTitle());
        assertEquals("Engineering", result.getDepartment());
        assertEquals("John", result.getFirstName()); // Should remain unchanged

        verify(employeeRepository).findById(employeeId);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toResponseDto(updatedEmployee);
    }

    @Test
    void deleteEmployee_Success() {
        // Given
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        // When
        employeeService.deleteEmployee(employeeId);

        // Then
        verify(employeeRepository).existsById(employeeId);
        verify(employeeRepository).deleteById(employeeId);
    }

    @Test
    void deleteEmployee_WhenNotFound_ThrowsException() {
        // Given
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        // When & Then
        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployee(employeeId));

        verify(employeeRepository).existsById(employeeId);
        verify(employeeRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void existsById_ReturnsTrue() {
        // Given
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        // When
        boolean result = employeeService.existsById(employeeId);

        // Then
        assertTrue(result);
        verify(employeeRepository).existsById(employeeId);
    }

    @Test
    void existsById_ReturnsFalse() {
        // Given
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        // When
        boolean result = employeeService.existsById(employeeId);

        // Then
        assertFalse(result);
        verify(employeeRepository).existsById(employeeId);
    }

    @Test
    void existsByAccountId_ReturnsTrue() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.of(employee));

        // When
        boolean result = employeeService.existsByAccountId(accountId);

        // Then
        assertTrue(result);
        verify(employeeRepository).findByAccountId(accountId);
    }

    @Test
    void existsByAccountId_ReturnsFalse() {
        // Given
        when(employeeRepository.findByAccountId(accountId)).thenReturn(Optional.empty());

        // When
        boolean result = employeeService.existsByAccountId(accountId);

        // Then
        assertFalse(result);
        verify(employeeRepository).findByAccountId(accountId);
    }

    @Test
    void getEmployeesByDepartment_Success() {
        // Given
        String department = "IT";
        List<Employee> employees = List.of(employee);
        when(employeeRepository.findByDepartmentContainingIgnoreCase(department)).thenReturn(employees);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        List<EmployeeResponseDto> result = employeeService.getEmployeesByDepartment(department);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository).findByDepartmentContainingIgnoreCase(department);
        verify(employeeMapper).toResponseDto(employee);
    }

    @Test
    void getEmployeesByJobTitle_Success() {
        // Given
        String jobTitle = "Software Engineer";
        List<Employee> employees = List.of(employee);
        when(employeeRepository.findByJobTitleContainingIgnoreCase(jobTitle)).thenReturn(employees);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        List<EmployeeResponseDto> result = employeeService.getEmployeesByJobTitle(jobTitle);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository).findByJobTitleContainingIgnoreCase(jobTitle);
        verify(employeeMapper).toResponseDto(employee);
    }

    @Test
    void getEmployeesHiredAfter_Success() {
        // Given
        LocalDate date = LocalDate.of(2022, 12, 31);
        List<Employee> employees = List.of(employee);
        when(employeeRepository.findByHiredDateAfter(date)).thenReturn(employees);
        when(employeeMapper.toResponseDto(employee)).thenReturn(employeeResponseDto);

        // When
        List<EmployeeResponseDto> result = employeeService.getEmployeesHiredAfter(date);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(employeeRepository).findByHiredDateAfter(date);
        verify(employeeMapper).toResponseDto(employee);
    }
}