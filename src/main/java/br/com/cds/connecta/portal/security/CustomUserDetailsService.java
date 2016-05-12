package br.com.cds.connecta.portal.security;

import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nataniel Paiva
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userRepository;

    @Autowired
    public CustomUserDetailsService(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", login));
        }
        return new UserRepositoryUserDetails(user);
    }

}
