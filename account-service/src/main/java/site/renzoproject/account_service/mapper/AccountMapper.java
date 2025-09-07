package site.renzoproject.account_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import site.renzoproject.account_service.dto.AccountCreateDto;
import site.renzoproject.account_service.dto.AccountListDto;
import site.renzoproject.account_service.dto.AccountResponseDto;
import site.renzoproject.account_service.dto.AccountUpdateDto;
import site.renzoproject.account_service.model.Account;

@Mapper(componentModel = "spring", uses = {AddressMapper.class,
        ContactInformationMapper.class,
        EmergencyContactMapper.class})
public interface AccountMapper {

    // AccountCreateDto to Account
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "address",source = "addressDto")
    @Mapping(target = "contactInformation", source = "contactInformationDto")
    @Mapping(target = "emergencyContact", source = "emergencyContactDto")
    @Mapping(target = "status", source = "status")
    Account toEntity(AccountCreateDto dto);

    // AccountUpdateDto to Account (for updates)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "status", source = "status")
    void updateAccountFromDto(AccountUpdateDto dto, @MappingTarget Account account);

    // Account to AccountResponseDto
    @Mapping(target = "addressDto", source = "address")
    @Mapping(target = "contactInformationDto", source = "contactInformation")
    @Mapping(target = "emergencyContactDto", source = "emergencyContact")
    @Mapping(target = "status", source = "status")
    AccountResponseDto toResponseDto(Account account);

    // Account to AccountListDto
    @Mapping(target = "imageUrl", source = "imageUrl")
    AccountListDto toListDto(Account account);
}
