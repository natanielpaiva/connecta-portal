package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;

/**
 *
 * @author Julio Lemes
 * @date Jul 24, 2015
 */
public interface IAuthenticationAS {
    
    AuthenticationDTO authenticate(String username, String password);
    
    AuthenticationDTO getAuthenticatedUser(String userToken);
    
    void logout(String userToken);

}
