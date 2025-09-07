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
    Account toEntity(AccountCreateDto dto);

    // AccountUpdateDto to Account (for updates)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    void updateAccountFromDto(AccountUpdateDto dto, @MappingTarget Account account);

    // Account to AccountResponseDto
    AccountResponseDto toResponseDto(Account account);

    // Account to AccountListDto
    @Mapping(target = "imageUrl", source = "imageUrl")
    AccountListDto toListDto(Account account);
}
