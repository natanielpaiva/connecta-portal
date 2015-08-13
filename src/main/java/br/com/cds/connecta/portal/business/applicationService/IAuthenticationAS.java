package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.portal.domain.security.UserDTO;

/**
 *
 * @author Julio Lemes
 * @date Jul 24, 2015
 */
public interface IAuthenticationAS {
    
    AuthenticationDTO authenticate(String username, String password);
    
    AuthenticationDTO authenticate(UserDTO user);
    
    AuthenticationDTO getAuthenticatedUser(String userToken);
    
    void logout(String userToken);

}
