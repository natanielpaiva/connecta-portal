package br.com.cds.connecta.portal.security.authentication.token.verify;
/**
 *
 * @author Julio Lemes
 * @date Aug 13, 2015
 */
public interface IAuthenticationTokenVerifier {
    
    Boolean verifyToken(String token, String userEmail);

}
