package br.com.cds.connecta.portal.security.connector;

import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.security.SecurityConnector;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Julio Lemes
 * @date Aug 6, 2015
 */
public class PortalSecurityConnector extends SecurityConnector {
    
    @Autowired IAuthenticationAS authAS;
    Logger log = Logger.getLogger(this.getClass());

    @Override
    public AuthenticationDTO getAuthenticatedUser(String token) {
        try {
            return authAS.getAuthenticatedUser(token);
        } catch (Exception ex) {
            log.error("Erro no processo de verificação de autenticação por token", ex);
            return null;
        }
    }

}
