package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.framework.core.domain.security.UserDTO;

/**
 *
 * @author Julio Lemes
 * @date Jul 24, 2015
 */
public interface IAuthenticationAS {
    
    UserDTO authenticate(String username, String password);
    
    UserDTO getAuthenticatedUser(String userToken);
    
    void logout(String userToken);

}
