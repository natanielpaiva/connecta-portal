package br.com.cds.connecta.portal.security;

import br.com.cds.connecta.portal.entity.User;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author nataniel
 */
public class UserRepositoryUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final User user;

    public UserRepositoryUserDetails(User user) {
        this.user = user;
        this.user.setDomains(null);
        this.user.setImage(null);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    public User getUser() {
        return user;
    }

}
