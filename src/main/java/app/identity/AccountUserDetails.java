package app.identity;

import app.entity.identity.Account;
import app.enumeration.Activity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class AccountUserDetails implements UserDetails {
    private Account account;

    public AccountUserDetails(Account account) {
        this.account = account;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        this.account.getAccountRoles().forEach(accountRole -> {
            grantedAuthorities.add(new Authority(accountRole.getRole().getName()));
        });
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return account.getDetails().getPasswordHash();
    }

    @Override
    public String getUsername() {
        return account.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.getActive().equals(Activity.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.getActive().equals(Activity.INACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return account.getActive().equals(Activity.ACTIVE);
    }
}
