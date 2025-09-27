package site.renzoproject.employee_service.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import site.renzoproject.employee_service.dto.EmployeeScheduleRequestDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleResponseDto;
import site.renzoproject.employee_service.dto.EmployeeScheduleSummaryDto;
import site.renzoproject.employee_service.model.Attendance;
import site.renzoproject.employee_service.model.EmployeeSchedule;
import site.renzoproject.employee_service.model.EmployeeScheduleAssignment;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeScheduleMapperTest {

    private final EmployeeScheduleMapper scheduleMapper = new EmployeeScheduleMapperImpl();

    @Test
    void testToEntity_FromEmployeeScheduleRequestDto() {
        // Given
        Instant startTime = Instant.now();
        Instant endTime = Instant.now().plusSeconds(28800); // 8 hours later

        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description("Morning Shift Schedule")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        // When
        EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

        // Then
        assertNotNull(schedule);
        assertNull(schedule.getId()); // ID should be ignored
        assertEquals("Morning Shift Schedule", schedule.getDescription());
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
        assertEquals("SHIFT", schedule.getScheduleType());
        assertEquals("APPROVED", schedule.getStatus());

        // Relationships and audit fields should be ignored
        assertNull(schedule.getAssignments());
        assertNull(schedule.getAttendances());
        assertNull(schedule.getCreatedAt());
        assertNull(schedule.getUpdatedAt());
        assertNull(schedule.getCreatedBy());
        assertNull(schedule.getModifiedBy());
    }

    @Test
    void testToEntity_WithNullValues() {
        // Given
        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description(null)
                .startTime(null)
                .endTime(null)
                .scheduleType(null)
                .status(null)
                .build();

        // When
        EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

        // Then
        assertNotNull(schedule);
        assertNull(schedule.getDescription());
        assertNull(schedule.getStartTime());
        assertNull(schedule.getEndTime());
        assertNull(schedule.getScheduleType());
        assertNull(schedule.getStatus());
    }

    @Test
    void testToEntity_WithEmptyDescription() {
        // Given
        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description("") // Empty string
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(3600))
                .scheduleType("TRAINING")
                .status("PLANNED")
                .build();

        // When
        EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

        // Then
        assertNotNull(schedule);
        assertEquals("", schedule.getDescription());
        assertEquals("TRAINING", schedule.getScheduleType());
        assertEquals("PLANNED", schedule.getStatus());
    }

    @Test
    void testToResponseDto_FromEmployeeSchedule() {
        // Given
        UUID scheduleId = UUID.randomUUID();
        Instant startTime = Instant.now();
        Instant endTime = Instant.now().plusSeconds(14400); // 4 hours later
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        // Create assignments set
        Set<EmployeeScheduleAssignment> assignments = new HashSet<>();
        assignments.add(EmployeeScheduleAssignment.builder()
                .id(UUID.randomUUID())
                .role("Trainer")
                .build());

        // Create attendances set
        Set<Attendance> attendances = new HashSet<>();
        attendances.add(Attendance.builder()
                .id(UUID.randomUUID())
                .status("PRESENT")
                .build());

        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(scheduleId)
                .description("Weekend Training Session")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("TRAINING")
                .status("APPROVED")
                .assignments(assignments)
                .attendances(attendances)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy("manager")
                .modifiedBy("admin")
                .build();

        // When
        EmployeeScheduleResponseDto responseDto = scheduleMapper.toResponseDto(schedule);

        // Then
        assertNotNull(responseDto);
        assertEquals(scheduleId, responseDto.getId());
        assertEquals("Weekend Training Session", responseDto.getDescription());
        assertEquals(startTime, responseDto.getStartTime());
        assertEquals(endTime, responseDto.getEndTime());
        assertEquals("TRAINING", responseDto.getScheduleType());
        assertEquals("APPROVED", responseDto.getStatus());
        assertNotNull(responseDto.getAssignments());
        assertEquals(1, responseDto.getAssignments().size());
        assertEquals(createdAt, responseDto.getCreatedAt());
        assertEquals(updatedAt, responseDto.getUpdatedAt());
        assertEquals("manager", responseDto.getCreatedBy());
        assertEquals("admin", responseDto.getModifiedBy());
    }

    @Test
    void testToResponseDto_WithEmptyAssignmentsAndAttendances() {
        // Given
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Empty Schedule")
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(7200))
                .scheduleType("LEAVE")
                .status("PLANNED")
                .assignments(new HashSet<>()) // Empty assignments
                .attendances(new HashSet<>()) // Empty attendances
                .build();

        // When
        EmployeeScheduleResponseDto responseDto = scheduleMapper.toResponseDto(schedule);

        // Then
        assertNotNull(responseDto);
        assertEquals("Empty Schedule", responseDto.getDescription());
        assertEquals("LEAVE", responseDto.getScheduleType());
        assertEquals("PLANNED", responseDto.getStatus());
        assertNotNull(responseDto.getAssignments());
        assertTrue(responseDto.getAssignments().isEmpty());
    }

    @Test
    void testToResponseDto_WithNullRelationships() {
        // Given
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Schedule with null relationships")
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(3600))
                .scheduleType("USAGE")
                .status("CANCELLED")
                .assignments(null) // Null assignments
                .attendances(null) // Null attendances
                .build();

        // When
        EmployeeScheduleResponseDto responseDto = scheduleMapper.toResponseDto(schedule);

        // Then
        assertNotNull(responseDto);
        assertEquals("Schedule with null relationships", responseDto.getDescription());
        assertEquals("USAGE", responseDto.getScheduleType());
        assertEquals("CANCELLED", responseDto.getStatus());
        assertNull(responseDto.getAssignments()); // Should remain null
    }

    @Test
    void testToSummaryDto_FromEmployeeSchedule() {
        // Given
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Quick Summary Schedule")
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(10800)) // 3 hours later
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        // When
        EmployeeScheduleSummaryDto summaryDto = scheduleMapper.toSummaryDto(schedule);

        // Then
        assertNotNull(summaryDto);
        assertEquals(schedule.getId(), summaryDto.getId());
        assertEquals("Quick Summary Schedule", summaryDto.getDescription());
        assertEquals(schedule.getStartTime(), summaryDto.getStartTime());
        assertEquals(schedule.getEndTime(), summaryDto.getEndTime());
        assertEquals("SHIFT", summaryDto.getScheduleType());
        assertEquals("APPROVED", summaryDto.getStatus());

        // Summary should not contain relationships or audit fields
        // (verify by checking that the summary DTO class doesn't have these fields)
    }

    @Test
    void testToSummaryDto_WithMinimalData() {
        // Given
        EmployeeSchedule schedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(3600))
                .scheduleType("TRAINING")
                // Description and status are null
                .build();

        // When
        EmployeeScheduleSummaryDto summaryDto = scheduleMapper.toSummaryDto(schedule);

        // Then
        assertNotNull(summaryDto);
        assertEquals(schedule.getId(), summaryDto.getId());
        assertNull(summaryDto.getDescription());
        assertEquals(schedule.getStartTime(), summaryDto.getStartTime());
        assertEquals(schedule.getEndTime(), summaryDto.getEndTime());
        assertEquals("TRAINING", summaryDto.getScheduleType());
        assertNull(summaryDto.getStatus());
    }

    @Test
    void testUpdateEntityFromDto() {
        // Given
        UUID existingScheduleId = UUID.randomUUID();
        LocalDateTime existingCreatedAt = LocalDateTime.now().minusDays(2);

        EmployeeSchedule existingSchedule = EmployeeSchedule.builder()
                .id(existingScheduleId)
                .description("Old Description")
                .startTime(Instant.now().minusSeconds(86400)) // 1 day ago
                .endTime(Instant.now().minusSeconds(82800)) // 23 hours ago
                .scheduleType("OLD_TYPE")
                .status("PLANNED")
                .createdAt(existingCreatedAt)
                .createdBy("oldUser")
                .build();

        Instant newStartTime = Instant.now().plusSeconds(3600); // 1 hour from now
        Instant newEndTime = Instant.now().plusSeconds(7200); // 2 hours from now

        EmployeeScheduleRequestDto updateDto = EmployeeScheduleRequestDto.builder()
                .description("New Description")
                .startTime(newStartTime)
                .endTime(newEndTime)
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        // When
        scheduleMapper.updateEntityFromDto(updateDto, existingSchedule);

        // Then
        // ID and audit fields should remain unchanged
        assertEquals(existingScheduleId, existingSchedule.getId());
        assertEquals(existingCreatedAt, existingSchedule.getCreatedAt());
        assertEquals("oldUser", existingSchedule.getCreatedBy());

        // Other fields should be updated
        assertEquals("New Description", existingSchedule.getDescription());
        assertEquals(newStartTime, existingSchedule.getStartTime());
        assertEquals(newEndTime, existingSchedule.getEndTime());
        assertEquals("SHIFT", existingSchedule.getScheduleType());
        assertEquals("APPROVED", existingSchedule.getStatus());

        // Relationships should remain null (ignored during update)
        assertNull(existingSchedule.getAssignments());
        assertNull(existingSchedule.getAttendances());
    }

    @Test
    void testUpdateEntityFromDto_WithNullValues() {
        // Given
        EmployeeSchedule existingSchedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Existing Description")
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(3600))
                .scheduleType("EXISTING")
                .status("ACTIVE")
                .build();

        EmployeeScheduleRequestDto updateDto = EmployeeScheduleRequestDto.builder()
                .description(null)
                .startTime(null)
                .endTime(null)
                .scheduleType(null)
                .status(null)
                .build();

        // When
        scheduleMapper.updateEntityFromDto(updateDto, existingSchedule);

        // Then
        // Fields should be set to null from the DTO
        assertNull(existingSchedule.getDescription());
        assertNull(existingSchedule.getStartTime());
        assertNull(existingSchedule.getEndTime());
        assertNull(existingSchedule.getScheduleType());
        assertNull(existingSchedule.getStatus());
    }

    @Test
    void testUpdateEntityFromDto_WithPartialUpdate() {
        // Given
        Instant originalStartTime = Instant.now();
        Instant originalEndTime = Instant.now().plusSeconds(3600);

        EmployeeSchedule existingSchedule = EmployeeSchedule.builder()
                .id(UUID.randomUUID())
                .description("Original Description")
                .startTime(originalStartTime)
                .endTime(originalEndTime)
                .scheduleType("ORIGINAL")
                .status("PENDING")
                .build();

        // Only update description and status, keep other fields unchanged
        EmployeeScheduleRequestDto updateDto = EmployeeScheduleRequestDto.builder()
                .description("Updated Description")
                .startTime(null) // Explicitly null - should set to null
                .endTime(originalEndTime) // Same as original
                .scheduleType("UPDATED")
                .status("APPROVED")
                .build();

        // When
        scheduleMapper.updateEntityFromDto(updateDto, existingSchedule);

        // Then
        assertEquals("Updated Description", existingSchedule.getDescription());
        assertNull(existingSchedule.getStartTime()); // Set to null from DTO
        assertEquals(originalEndTime, existingSchedule.getEndTime()); // Unchanged
        assertEquals("UPDATED", existingSchedule.getScheduleType());
        assertEquals("APPROVED", existingSchedule.getStatus());
    }

    @Test
    void testMapIdToEmployeeSchedule_HelperMethod() {
        // Given
        UUID scheduleId = UUID.randomUUID();

        // When
        EmployeeSchedule schedule = scheduleMapper.mapIdToEmployeeSchedule(scheduleId);

        // Then
        assertNotNull(schedule);
        assertEquals(scheduleId, schedule.getId());
        assertNull(schedule.getDescription()); // Only ID should be set
        assertNull(schedule.getStartTime());
        assertNull(schedule.getEndTime());
    }

    @Test
    void testMapIdToEmployeeSchedule_WithNullId() {
        // When
        EmployeeSchedule schedule = scheduleMapper.mapIdToEmployeeSchedule(null);

        // Then
        assertNull(schedule);
    }

    @Test
    void testDifferentScheduleTypes() {
        // Given
        String[] scheduleTypes = {"SHIFT", "LEAVE", "TRAINING", "USAGE"};
        String[] statuses = {"PLANNED", "APPROVED", "CANCELLED"};

        for (String scheduleType : scheduleTypes) {
            for (String status : statuses) {
                EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                        .description(scheduleType + " - " + status)
                        .startTime(Instant.now())
                        .endTime(Instant.now().plusSeconds(3600))
                        .scheduleType(scheduleType)
                        .status(status)
                        .build();

                // When
                EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

                // Then
                assertNotNull(schedule);
                assertEquals(scheduleType, schedule.getScheduleType());
                assertEquals(status, schedule.getStatus());
                assertEquals(scheduleType + " - " + status, schedule.getDescription());
            }
        }
    }

    @Test
    void testScheduleWithVeryLongDescription() {
        // Given
        String longDescription = "This is a very long description for a schedule that might be used for " +
                "detailed explanations of what the schedule entails. It could include specific instructions, " +
                "notes, or other important information that needs to be stored with the schedule.";

        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description(longDescription)
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(14400)) // 4 hours
                .scheduleType("TRAINING")
                .status("APPROVED")
                .build();

        // When
        EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

        // Then
        assertNotNull(schedule);
        assertEquals(longDescription, schedule.getDescription());
        assertEquals("TRAINING", schedule.getScheduleType());
    }

    @Test
    void testScheduleTimeValidation_StartBeforeEnd() {
        // Given
        Instant startTime = Instant.now();
        Instant endTime = startTime.plusSeconds(7200); // 2 hours after start

        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description("Valid time range")
                .startTime(startTime)
                .endTime(endTime)
                .scheduleType("SHIFT")
                .status("PLANNED")
                .build();

        // When
        EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

        // Then - Should not throw exception for valid time range
        assertNotNull(schedule);
        assertEquals(startTime, schedule.getStartTime());
        assertEquals(endTime, schedule.getEndTime());
    }

    @Test
    void testResponseDto_AllFieldsNull() {
        // Given
        EmployeeSchedule schedule = new EmployeeSchedule(); // All fields null

        // When
        EmployeeScheduleResponseDto responseDto = scheduleMapper.toResponseDto(schedule);

        // Then
        assertNotNull(responseDto);
        assertNull(responseDto.getId());
        assertNull(responseDto.getDescription());
        assertNull(responseDto.getStartTime());
        assertNull(responseDto.getEndTime());
        assertNull(responseDto.getScheduleType());
        assertNull(responseDto.getStatus());
        assertTrue(responseDto.getAssignments().isEmpty());
        assertNull(responseDto.getCreatedAt());
        assertNull(responseDto.getUpdatedAt());
        assertNull(responseDto.getCreatedBy());
        assertNull(responseDto.getModifiedBy());
    }

    @Test
    void testSummaryDto_AllFieldsNull() {
        // Given
        EmployeeSchedule schedule = new EmployeeSchedule(); // All fields null

        // When
        EmployeeScheduleSummaryDto summaryDto = scheduleMapper.toSummaryDto(schedule);

        // Then
        assertNotNull(summaryDto);
        assertNull(summaryDto.getId());
        assertNull(summaryDto.getDescription());
        assertNull(summaryDto.getStartTime());
        assertNull(summaryDto.getEndTime());
        assertNull(summaryDto.getScheduleType());
        assertNull(summaryDto.getStatus());
    }

    @Test
    void testMultipleMappings_Consistency() {
        // Given
        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description("Consistency Test")
                .startTime(Instant.now())
                .endTime(Instant.now().plusSeconds(5400)) // 1.5 hours
                .scheduleType("SHIFT")
                .status("APPROVED")
                .build();

        // When - Map multiple times to ensure consistency
        EmployeeSchedule schedule1 = scheduleMapper.toEntity(requestDto);
        EmployeeSchedule schedule2 = scheduleMapper.toEntity(requestDto);

        // Then
        assertNotNull(schedule1);
        assertNotNull(schedule2);
        assertEquals(schedule1.getDescription(), schedule2.getDescription());
        assertEquals(schedule1.getStartTime(), schedule2.getStartTime());
        assertEquals(schedule1.getEndTime(), schedule2.getEndTime());
        assertEquals(schedule1.getScheduleType(), schedule2.getScheduleType());
        assertEquals(schedule1.getStatus(), schedule2.getStatus());

        // They should be different instances
        assertNotSame(schedule1, schedule2);
    }

    @Test
    void testScheduleWithSameStartAndEndTime() {
        // Given
        Instant sameTime = Instant.now();

        EmployeeScheduleRequestDto requestDto = EmployeeScheduleRequestDto.builder()
                .description("Instant schedule")
                .startTime(sameTime)
                .endTime(sameTime) // Same as start time
                .scheduleType("MEETING")
                .status("PLANNED")
                .build();

        // When
        EmployeeSchedule schedule = scheduleMapper.toEntity(requestDto);

        // Then - Should handle same start/end time without issues
        assertNotNull(schedule);
        assertEquals(sameTime, schedule.getStartTime());
        assertEquals(sameTime, schedule.getEndTime());
    }
}