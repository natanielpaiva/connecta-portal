package br.com.cds.connecta.portal.security;

import javax.transaction.Transactional;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 *
 * @author Nataniel Paiva
 */
public class PortalTokenServices extends DefaultTokenServices {

    @Override
    @Transactional
    public synchronized OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        return super.createAccessToken(authentication);
    }
}
