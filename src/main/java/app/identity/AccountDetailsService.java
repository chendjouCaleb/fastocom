package app.identity;

import app.entity.identity.Account;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Qualifier
public class AccountDetailsService implements UserDetailsService{
    private IdentityManager identityManager;

    public AccountDetailsService(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account;
        try {
            account = identityManager.findAccountByEmail(username);
        }catch (NoSuchElementException ex){
            try {
                account = identityManager.findAccountByPhone(username);
            }catch (NoSuchElementException ignore){
                throw new UsernameNotFoundException("Aucun compte ne possède " + username + " comme email ou téléphone");
            }
        }
        return new AccountUserDetails(account);
    }


}
