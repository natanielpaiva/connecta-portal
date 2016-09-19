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
import br.com.cds.connecta.portal.business.applicationService.ILdapAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Role;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.RoleDAO;
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
    private RoleDAO roleRepository;

	private User createUser(String username,String name){

		User user = new User();
		user.setName(name);
		user.setEmail(username);
		user.setPassword(encoder.encode("044063c23354128b336df86f11872e68")); //md5 for ldap

//		Role roleadm = roleRepository.findOne(RoleSpecification.byName("ROLE_ADMIN"));
		Role roleUsr = roleRepository.findOne(RoleSpecification.byName("ROLE_USER"));

		List<Role> roles = new ArrayList<Role>();
//		roles.add(roleadm);
		roles.add(roleUsr);
		user.setRoles(roles);
		
		return userService.save(user);
	}

	private Authentication createAutentication(User user){
		
		UserRepositoryUserDetails userDetails = new UserRepositoryUserDetails(user);

		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();

		for (Role role: user.getRoles()){
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

		if (ldapUser.getUsername() == null){
			throw new AuthenticationServiceException("Login ou Senha inválidos");
		}

		User user = null;

		try{
			user = userService.getByEmail(login);
		}catch(ResourceNotFoundException e){
			user = createUser(login, ldapUser.getName());
		}

		Authentication auth = createAutentication(user);

		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

