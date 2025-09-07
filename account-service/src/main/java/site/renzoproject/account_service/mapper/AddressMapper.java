package site.renzoproject.account_service.mapper;

import org.mapstruct.Mapper;
import site.renzoproject.account_service.dto.AddressDto;
import site.renzoproject.account_service.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address toEntity(AddressDto dto);
    AddressDto toDto(Address entity);
}
