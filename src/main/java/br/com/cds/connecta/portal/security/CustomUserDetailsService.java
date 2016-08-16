package br.com.cds.connecta.portal.security;

import br.com.cds.connecta.portal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.cds.connecta.portal.persistence.UserRepository;

/**
 *
 * @author Nataniel Paiva
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", login));
        }
        return new UserRepositoryUserDetails(user);
    }

}
