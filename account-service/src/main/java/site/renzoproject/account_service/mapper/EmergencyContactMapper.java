package site.renzoproject.account_service.mapper;

import org.mapstruct.Mapper;
import site.renzoproject.account_service.dto.EmergencyContactDto;
import site.renzoproject.account_service.model.EmergencyContact;

@Mapper(componentModel = "spring")
public interface EmergencyContactMapper {
    EmergencyContact toEntity(EmergencyContactDto dto);
    EmergencyContactDto toDto(EmergencyContact entity);
}
