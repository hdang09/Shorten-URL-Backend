package hdang09.services;

import hdang09.constants.Role;
import hdang09.constants.Status;
import hdang09.dto.CreateAccountDTO;
import hdang09.entities.Account;
import hdang09.entities.Response;
import hdang09.mappers.AccountMapper;
import hdang09.repositories.AccountRepository;
import hdang09.utils.AuthorizationUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    private final AuthorizationUtil authorizationUtil;

    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper mapper, AuthorizationUtil authorizationUtil) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
        this.authorizationUtil = authorizationUtil;
    }

    public Response<List<Account>> getAll(HttpServletRequest request) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        // Get all accounts
        List<Account> accounts = accountRepository.getAll();
        if (accounts.isEmpty()) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "The account list is empty");
        }

        return new Response<>(HttpStatus.OK.value(), "Get all user successfully", accounts);
    }

    public Response<Account> createAccount(HttpServletRequest request, CreateAccountDTO createAccountDTO) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        try {
            // Map from DTO to entity
            Account account = mapper.fromDtoToEntity(createAccountDTO);

            // Check account if it not exists
            boolean isAccountExists = accountRepository.getByEmail(account.getEmail()) != null;
            if (isAccountExists) {
                return new Response<>(HttpStatus.BAD_REQUEST.value(), "Email is exist");
            }

            // Store to database
            Account newAccount = accountRepository.save(account);
            return new Response<>(HttpStatus.CREATED.value(), "Account created successfully", newAccount);
        } catch (Exception e) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Failed to create account");
        }
    }

    public Response<Account> updateStatus(HttpServletRequest request, Status status, int accountId) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        // Find account in database
        Account account = accountRepository.findById(accountId).orElse(null);

        // Check account if it not exists
        if (account == null) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "The user does not exist");
        }

        // Check account if it not exists
        if (account.getRole() == Role.ADMIN && status == Status.REJECT) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "Cannot reject admin, must change this account to user");
        }

        // Update status
        account.setStatus(status);
        return new Response<>(HttpStatus.OK.value(), "Update status successfully", accountRepository.save(account));
    }

    public Response<Account> updateRole(HttpServletRequest request, Role role, int accountId) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        // Find account in database
        Account account = accountRepository.findById(accountId).orElse(null);

        // Check account if it not exists
        if (account == null) {
            return new Response<>(HttpStatus.BAD_REQUEST.value(), "The user does not exist");
        }

        // Check account if user isn't accept
        if (account.getStatus() != Status.ACCEPT) {
            return new Response<>(
                    HttpStatus.UNAUTHORIZED.value(), "The user can not update role (status must be accept)"
            );
        }

        // Update status
        account.setRole(role);
        return new Response<>(HttpStatus.OK.value(), "Update role successfully", accountRepository.save(account));
    }

    public Response<Account> getUserById(HttpServletRequest request, int accountId) {
        // Check role
        Role currentRole = Role.fromInt(authorizationUtil.getRoleFromAuthorizationHeader(request));
        if (!currentRole.equals(Role.ADMIN)) {
            return new Response<>(HttpStatus.UNAUTHORIZED.value(), "The user is not admin");
        }

        // Find account in database
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        return new Response<>(HttpStatus.OK.value(), "Get user info successfully", account);
    }

    public Response<Account> getCurrentUser(HttpServletRequest request) {
        int accountId = authorizationUtil.getUserIdFromAuthorizationHeader(request);
        Account account = accountRepository.findById(accountId).orElse(null);

        if (account == null) {
            return new Response<>(HttpStatus.NOT_FOUND.value(), "Account does not exist");
        }

        return new Response<>(HttpStatus.OK.value(), "Get user info successfully", account);
    }
}
