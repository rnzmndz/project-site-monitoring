package site.renzoproject.employee_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final AccountClient accountClient;

    /**
     * Create a new employee
     */
    public EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {
        log.info("Creating new employee for account: {}", employeeRequestDto.getAccountId());

        // Check if employee already exists for this account
        if (employeeRepository.findByAccountId(employeeRequestDto.getAccountId()).isPresent()) {
            throw new DuplicateEmployeeException("Employee already exists for account ID: " + employeeRequestDto.getAccountId());
        }

        // Validate account exists
        validateAccountExists(employeeRequestDto.getAccountId());

        Employee employee = employeeMapper.toEntity(employeeRequestDto);
        employee.setId(UUID.randomUUID());

        Employee savedEmployee = employeeRepository.save(employee);
        log.info("Successfully created employee with ID: {}", savedEmployee.getId());

        return employeeMapper.toResponseDto(savedEmployee);
    }

    /**
     * Get all employees with pagination
     */
    @Transactional(readOnly = true)
    public EmployeePage getAllEmployees(Pageable pageable) {
        log.info("Fetching all employees with pageable: {}", pageable);

        Page<Employee> employeePage = employeeRepository.findAll(pageable);
        List<EmployeeResponseDto> content = employeePage.getContent()
                .stream()
                .map(employeeMapper::toResponseDto)
                .toList();

        log.info("Found {} employees", employeePage.getTotalElements());
        return new EmployeePage(content, pageable, employeePage.getTotalElements());
    }

    /**
     * Get employee by ID
     */
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeById(UUID id) {
        log.info("Fetching employee with ID: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return employeeMapper.toResponseDto(employee);
    }

    /**
     * Get employee by account ID
     */
    @Transactional(readOnly = true)
    public EmployeeResponseDto getEmployeeByAccountId(UUID accountId) {
        log.info("Fetching employee with account ID: {}", accountId);

        Employee employee = employeeRepository.findByAccountId(accountId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with account id: " + accountId));
        return employeeMapper.toResponseDto(employee);
    }

    /**
     * Update employee
     */
    public EmployeeResponseDto updateEmployee(UUID id, EmployeeRequestDto employeeRequestDto) {
        log.info("Updating employee with ID: {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        // Check if account ID is being changed and validate the new account exists
        if (!existingEmployee.getAccountId().equals(employeeRequestDto.getAccountId())) {
            // Check if new account ID is already associated with another employee
            Optional<Employee> employeeWithNewAccount = employeeRepository.findByAccountId(employeeRequestDto.getAccountId());
            if (employeeWithNewAccount.isPresent() && !employeeWithNewAccount.get().getId().equals(id)) {
                throw new DuplicateEmployeeException("Another employee already exists for account ID: " + employeeRequestDto.getAccountId());
            }
            validateAccountExists(employeeRequestDto.getAccountId());
        }

        employeeMapper.updateEntityFromDto(employeeRequestDto, existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        log.info("Successfully updated employee with ID: {}", id);
        return employeeMapper.toResponseDto(updatedEmployee);
    }

    /**
     * Partial update employee
     */
    public EmployeeResponseDto partialUpdateEmployee(UUID id, EmployeeRequestDto employeeRequestDto) {
        log.info("Partially updating employee with ID: {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));

        // Update only non-null fields from request DTO
        if (employeeRequestDto.getAccountId() != null &&
                !existingEmployee.getAccountId().equals(employeeRequestDto.getAccountId())) {

            // Validate new account and check for duplicates
            Optional<Employee> employeeWithNewAccount = employeeRepository.findByAccountId(employeeRequestDto.getAccountId());
            if (employeeWithNewAccount.isPresent() && !employeeWithNewAccount.get().getId().equals(id)) {
                throw new DuplicateEmployeeException("Another employee already exists for account ID: " + employeeRequestDto.getAccountId());
            }
            validateAccountExists(employeeRequestDto.getAccountId());
            existingEmployee.setAccountId(employeeRequestDto.getAccountId());
        }

        if (employeeRequestDto.getFirstName() != null) {
            existingEmployee.setFirstName(employeeRequestDto.getFirstName());
        }
        if (employeeRequestDto.getMiddleName() != null) {
            existingEmployee.setMiddleName(employeeRequestDto.getMiddleName());
        }
        if (employeeRequestDto.getLastName() != null) {
            existingEmployee.setLastName(employeeRequestDto.getLastName());
        }
        if (employeeRequestDto.getNameSuffix() != null) {
            existingEmployee.setNameSuffix(employeeRequestDto.getNameSuffix());
        }
        if (employeeRequestDto.getJobTitle() != null) {
            existingEmployee.setJobTitle(employeeRequestDto.getJobTitle());
        }
        if (employeeRequestDto.getDepartment() != null) {
            existingEmployee.setDepartment(employeeRequestDto.getDepartment());
        }
        if (employeeRequestDto.getHiredDate() != null) {
            existingEmployee.setHiredDate(employeeRequestDto.getHiredDate());
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        log.info("Successfully partially updated employee with ID: {}", id);
        return employeeMapper.toResponseDto(updatedEmployee);
    }

    /**
     * Delete employee
     */
    public void deleteEmployee(UUID id) {
        log.info("Deleting employee with ID: {}", id);

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
        log.info("Successfully deleted employee with ID: {}", id);
    }

    /**
     * Check if employee exists by ID
     */
    @Transactional(readOnly = true)
    public boolean existsById(UUID id) {
        log.debug("Checking if employee exists with ID: {}", id);
        return employeeRepository.existsById(id);
    }

    /**
     * Check if employee exists by account ID
     */
    @Transactional(readOnly = true)
    public boolean existsByAccountId(UUID accountId) {
        log.debug("Checking if employee exists with account ID: {}", accountId);
        return employeeRepository.findByAccountId(accountId).isPresent();
    }

    /**
     * Search employees by department
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByDepartment(String department) {
        log.info("Searching employees by department: {}", department);

        List<Employee> employees = employeeRepository.findByDepartmentContainingIgnoreCase(department);
        return employees.stream()
                .map(employeeMapper::toResponseDto)
                .toList();
    }

    /**
     * Search employees by job title
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesByJobTitle(String jobTitle) {
        log.info("Searching employees by job title: {}", jobTitle);

        List<Employee> employees = employeeRepository.findByJobTitleContainingIgnoreCase(jobTitle);
        return employees.stream()
                .map(employeeMapper::toResponseDto)
                .toList();
    }

    /**
     * Get employees hired after a specific date
     */
    @Transactional(readOnly = true)
    public List<EmployeeResponseDto> getEmployeesHiredAfter(java.time.LocalDate date) {
        log.info("Searching employees hired after: {}", date);

        List<Employee> employees = employeeRepository.findByHiredDateAfter(date);
        return employees.stream()
                .map(employeeMapper::toResponseDto)
                .toList();
    }

    /**
     * Validate that account exists in account service
     */
    private void validateAccountExists(UUID accountId) {
        log.info("Validating account existence for account ID: {}", accountId);

        try {
            AccountResponseDto accountResponse = accountClient.getAccountById(accountId);
            if (accountResponse == null) {
                throw new RuntimeException("Account not found with id: " + accountId);
            }
            log.debug("Account validation successful for account ID: {}", accountId);
        } catch (Exception e) {
            log.error("Error validating account with ID: {}", accountId, e);
            throw new RuntimeException("Account validation failed for id: " + accountId, e);
        }
    }
}