package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.domain.security.UserCredentialsDTO;
import br.com.cds.connecta.portal.domain.security.UserDTO;
import br.com.cds.connecta.portal.domain.security.UserProfileDTO;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public interface IUserAS {
    
    UserDTO createUser(UserDTO user);
    
    void updatePassword(String userId, UserCredentialsDTO credentials);
    
    void updateUser(UserProfileDTO user);
    
    void deleteUser(String userId);
    
}
