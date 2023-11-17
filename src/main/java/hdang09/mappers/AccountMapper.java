package hdang09.mappers;

import hdang09.dto.CreateAccountDTO;
import hdang09.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account fromDtoToEntity(CreateAccountDTO createAccountDTO) {
        Account account = new Account();

        account.setEmail(createAccountDTO.getEmail());
        account.setRole(createAccountDTO.getRole());
        account.setFirstName(createAccountDTO.getFirstName());
        account.setLastName(createAccountDTO.getLastName());

        return account;
    }
}
