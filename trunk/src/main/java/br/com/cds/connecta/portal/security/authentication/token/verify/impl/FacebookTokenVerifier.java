package br.com.cds.connecta.portal.security.authentication.token.verify.impl;

import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.domain.security.FacebookTokenVerifyDTO;
import br.com.cds.connecta.portal.security.authentication.token.verify.IAuthenticationTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio Lemes
 * @date Aug 13, 2015
 */
@Service
public class FacebookTokenVerifier implements IAuthenticationTokenVerifier {
    
    private @Autowired IApplicationConfigAS configAS;

    @Override
    public Boolean verifyToken(String token, String userEmail) {
        
        FacebookTokenVerifyDTO tokenVerify = tokenVerifyRequest(token);
        
        return Util.isNull(tokenVerify.getError()) && userEmail.equals(tokenVerify.getEmail());
        
    }
    
    private FacebookTokenVerifyDTO tokenVerifyRequest(String token){
        return RestClient.getForObject(getTokenVerifierUrl(), FacebookTokenVerifyDTO.class, token);
    }
    
    private String getTokenVerifierUrl(){
        return configAS.getByName(ApplicationConfigEnum.FACEBOOK_VERIFY_TOKEN_URL);
    }
    
}
