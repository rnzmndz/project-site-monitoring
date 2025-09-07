package site.renzoproject.account_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "accounts")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    private UUID id;

    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String nameSuffix;
    private LocalDate birthDate;
    private String imageUrl;
    private Boolean enabled;

    @Embedded
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    @Embedded
    private EmergencyContact emergencyContact;

    //Auditing fields
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;
}
