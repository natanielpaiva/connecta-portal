package br.com.cds.connecta.portal.security;

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
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        // CHECKSTYLE:ON
        OAuth2AccessToken token = super.createAccessToken(authentication);

        return token;
    }
}
