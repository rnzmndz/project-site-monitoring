package site.renzoproject.employee_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.renzoproject.employee_service.model.Leave;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeaveRepository extends JpaRepository<Leave, UUID> {

    /**
     * Find all leaves by employee ID
     */
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId")
    List<Leave> findByEmployeeId(@Param("employeeId") UUID employeeId);

    /**
     * Find all leaves by employee ID with pagination
     */
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId")
    Page<Leave> findByEmployeeId(@Param("employeeId") UUID employeeId, Pageable pageable);

    /**
     * Find all leaves by status
     */
    @Query("SELECT l FROM Leave l WHERE l.status = :status")
    List<Leave> findByStatus(@Param("status") String status);

    /**
     * Find all leaves by status with pagination
     */
    @Query("SELECT l FROM Leave l WHERE l.status = :status")
    Page<Leave> findByStatus(@Param("status") String status, Pageable pageable);

    /**
     * Find leaves that overlap with the given date range
     */
    @Query("SELECT l FROM Leave l WHERE " +
            "(l.startDate BETWEEN :startDate AND :endDate OR " +
            "l.endDate BETWEEN :startDate AND :endDate OR " +
            "(l.startDate <= :startDate AND l.endDate >= :endDate))")
    List<Leave> findByStartDateBetweenOrEndDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("startDate") LocalDate startDate2,
            @Param("endDate") LocalDate endDate2);

    /**
     * Find overlapping leaves for a specific employee
     */
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND " +
            "l.status != 'REJECTED' AND l.status != 'CANCELLED' AND " +
            "(l.startDate BETWEEN :startDate AND :endDate OR " +
            "l.endDate BETWEEN :startDate AND :endDate OR " +
            "(l.startDate <= :startDate AND l.endDate >= :endDate))")
    List<Leave> findOverlappingLeaves(
            @Param("employeeId") UUID employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    /**
     * Find overlapping leaves for a specific employee excluding a specific leave
     */
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND " +
            "l.id != :excludeLeaveId AND " +
            "l.status != 'REJECTED' AND l.status != 'CANCELLED' AND " +
            "(l.startDate BETWEEN :startDate AND :endDate OR " +
            "l.endDate BETWEEN :startDate AND :endDate OR " +
            "(l.startDate <= :startDate AND l.endDate >= :endDate))")
    List<Leave> findOverlappingLeavesExcludingId(
            @Param("employeeId") UUID employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("excludeLeaveId") UUID excludeLeaveId);

    /**
     * Find leaves by employee and status
     */
    @Query("SELECT l FROM Leave l WHERE l.employee.id = :employeeId AND l.status = :status")
    List<Leave> findByEmployeeIdAndStatus(@Param("employeeId") UUID employeeId, @Param("status") String status);

    /**
     * Count active leaves for an employee (APPROVED or REQUESTED)
     */
    @Query("SELECT COUNT(l) FROM Leave l WHERE l.employee.id = :employeeId AND l.status IN ('APPROVED', 'REQUESTED')")
    long countActiveLeavesByEmployeeId(@Param("employeeId") UUID employeeId);
}