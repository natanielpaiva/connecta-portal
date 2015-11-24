package br.com.cds.connecta.portal.domain.security;

import br.com.cds.connecta.portal.security.authentication.token.domain.SocialTokenType;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public class UserCredentialsDTO {

    private String password;
    private String authenticatedUserPassword;
    private String token;
    private SocialTokenType tokenType;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthenticatedUserPassword() {
        return authenticatedUserPassword;
    }

    public void setAuthenticatedUserPassword(String authenticatedUserPassword) {
        this.authenticatedUserPassword = authenticatedUserPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SocialTokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(SocialTokenType tokenType) {
        this.tokenType = tokenType;
    }

}
