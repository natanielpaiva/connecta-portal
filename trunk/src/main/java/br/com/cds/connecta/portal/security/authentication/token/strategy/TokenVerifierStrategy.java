package br.com.cds.connecta.portal.security.authentication.token.strategy;

import br.com.cds.connecta.framework.core.context.ConnectaSpringContext;
import br.com.cds.connecta.framework.core.domain.SpringBeanScope;
import br.com.cds.connecta.portal.security.authentication.token.verify.IAuthenticationTokenVerifier;
import br.com.cds.connecta.portal.security.authentication.token.domain.SocialTokenType;
import br.com.cds.connecta.portal.security.authentication.token.verify.impl.FacebookTokenVerifier;
import br.com.cds.connecta.portal.security.authentication.token.verify.impl.GoogleTokenVerifier;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Julio Lemes
 * @date Aug 13, 2015
 */
@Component
@Scope(SpringBeanScope.SINGLETON)
public class TokenVerifierStrategy {
    
    private final Map<SocialTokenType, Class<? extends IAuthenticationTokenVerifier>> verifiers = new HashMap<>();
    
    {
        verifiers.put(SocialTokenType.FACEBOOK, FacebookTokenVerifier.class);
        verifiers.put(SocialTokenType.GOOGLE, GoogleTokenVerifier.class);
    }
    
    public IAuthenticationTokenVerifier getVerifier(SocialTokenType type){
        return ConnectaSpringContext.getBean(verifiers.get(type));
    }
    
}
