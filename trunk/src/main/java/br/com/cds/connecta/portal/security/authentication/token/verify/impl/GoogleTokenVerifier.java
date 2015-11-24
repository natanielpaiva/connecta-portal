package br.com.cds.connecta.portal.security.authentication.token.verify.impl;

import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.domain.security.GooglePlusTokenVerifyDTO;
import br.com.cds.connecta.portal.security.authentication.token.verify.IAuthenticationTokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio Lemes
 * @date Aug 13, 2015
 */
@Service
public class GoogleTokenVerifier implements IAuthenticationTokenVerifier {
    
    private @Autowired IApplicationConfigAS configAS;

    @Override
    public Boolean verifyToken(String token, String userEmail) {
        GooglePlusTokenVerifyDTO tokenVerify = tokenVerifyRequest(token);
        
        return Util.isNotNull(tokenVerify) && Util.isEmpty(tokenVerify.getError()) && userEmail.equals(tokenVerify.getEmail());
    }

    private GooglePlusTokenVerifyDTO tokenVerifyRequest(String token) {
        GooglePlusTokenVerifyDTO tokenDTO;
        try {
            tokenDTO = RestClient.getForObject(getTokenVerifierUrl(), GooglePlusTokenVerifyDTO.class, token);
        } catch (Exception e) {
            return null;
        }
        return tokenDTO;
    }
    
    private String getTokenVerifierUrl(){
        return configAS.getByName(ApplicationConfigEnum.GOOGLE_VERIFY_TOKEN_URL);
    }

}
