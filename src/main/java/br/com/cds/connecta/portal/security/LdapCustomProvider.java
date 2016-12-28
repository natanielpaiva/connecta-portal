package br.com.cds.connecta.portal.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.ILdapAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.domain.UserProviderEnum;
import br.com.cds.connecta.portal.entity.Role;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.RoleRepository;
import br.com.cds.connecta.portal.persistence.specification.RoleSpecification;
import br.com.cds.connecta.portal.security.ldap.LdapUser;

@Component("LdapCustomProvider")
public class LdapCustomProvider implements AuthenticationProvider {

    @Autowired
    private ILdapAS ldapService;

    @Autowired
    private IUserAS userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    private User createUser(String username, String name) {

        User user = new User();
        user.setName(name);
        user.setEmail(username);
        user.setPassword(encoder.encode("044063c23354128b336df86f11872e68")); //md5 for ldap
        user.setProvider(UserProviderEnum.LDAP);

        return userService.save(user);
    }

    private Authentication createAutentication(User user) {

        UserRepositoryUserDetails userDetails = new UserRepositoryUserDetails(user);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();

        for (Role role : user.getRoles()) {
            grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getUser(), grantedAuths);

        return auth;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        LdapUser ldapUser = ldapService.verifyLogin(login, password);

        if (ldapUser == null || ldapUser.getUsername() == null) {
            throw new AuthenticationServiceException("Login ou Senha inválidos");
        }

        User user = null;

        try {
            user = userService.getByEmail(login);
            if (Util.isNotNull(user.getHashInvited())) {
                user = updateUser(user, ldapUser);
            }
        } catch (ResourceNotFoundException e) {
            user = createUser(login, ldapUser.getName());
        }

        Authentication auth = createAutentication(user);

        return auth;
    }

    private User updateUser(User user, LdapUser ldapUser) {
        user.setName(ldapUser.getName());
        user.setPassword(encoder.encode("044063c23354128b336df86f11872e68")); //md5 for ldap
        user.setProvider(UserProviderEnum.LDAP);
        Role roleUsr = roleRepository.findOne(RoleSpecification.byName("ROLE_USER"));


        List<Role> roles = new ArrayList<Role>();
        roles.add(roleUsr);
        user.setRoles(roles);


        //retira o hashInvited
        user.setHashInvited(null);

        return userService.update(user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
