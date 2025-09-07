package site.renzoproject.account_service.mapper;

import org.mapstruct.Mapper;
import site.renzoproject.account_service.dto.ContactInformationDto;
import site.renzoproject.account_service.model.ContactInformation;

@Mapper(componentModel = "spring")
public interface ContactInformationMapper {
    ContactInformation toEntity(ContactInformationDto dto);
    ContactInformationDto toDto(ContactInformation entity);
}
