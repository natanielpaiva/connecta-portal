package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.ExceptionEnum;
import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.util.SecurityUtil;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.security.authentication.token.domain.SocialTokenType;
import br.com.cds.connecta.portal.domain.security.UserDTO;
import br.com.cds.connecta.portal.security.authentication.token.strategy.TokenVerifierStrategy;
import br.com.cds.connecta.portal.security.authentication.token.verify.IAuthenticationTokenVerifier;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 *
 * @author Julio Lemes
 * @date Jul 24, 2015
 */
@Service
public class AuthenticationAS implements IAuthenticationAS {
    
    private static final String AVATAR_URL_TEMPLATE = "http://gravatar.com/avatar/%s?s=50&d=mm";

    private @Autowired IApplicationConfigAS config;
    private @Autowired TokenVerifierStrategy tokenVerifierStrategy;

    private String authProviderUrl;
    private String authResourceUrl;
    private String loginEndpoint;
    private String logoutEndpoint;
    private String authenticatedUserEndpoint;

    @Override
    public AuthenticationDTO authenticate(UserDTO user) {
        //Verificar se o login é por password ou token
        String token = user.getCredentials().getToken();
        Boolean hasToken = Util.isNotEmpty(token);

        if (hasToken) {
            SocialTokenType tokenType = user.getCredentials().getTokenType();
            
            //Verificar autenticidade do token na rede social
            IAuthenticationTokenVerifier verifier = tokenVerifierStrategy.getVerifier(tokenType);
            boolean isVerified = verifier.verifyToken(token, user.getProfile().getEmail());
            
            if(isVerified){
                UserDTO.handleTokenAuth(user);
            } else {
                throw new BusinessException(ExceptionEnum.REJECTED, "AUTH.TOKEN.ERROR");
            }
            
        }
        
        AuthenticationDTO auth = authenticate(user.getProfile().getId(), user.getCredentials().getPassword());
        return auth;
    }

    @Override
    public AuthenticationDTO authenticate(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        AuthenticationDTO authenticationDTO = RestClient.formRequest(getLoginEndpoint(), HttpMethod.POST, AuthenticationDTO.class, params, null);
        
        generateAvatarUrl(authenticationDTO);
        
        return authenticationDTO;
    }

    @Override
    public AuthenticationDTO getAuthenticatedUser(String userToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", userToken);
        
        AuthenticationDTO authenticationDTO = RestClient.formRequest(getAuthenticatedUserEndpoint(), HttpMethod.GET, AuthenticationDTO.class, null, headers);
        
        generateAvatarUrl(authenticationDTO);

        return authenticationDTO;
    }

    private void generateAvatarUrl(AuthenticationDTO authenticationDTO) {
        if (Util.isEmpty(authenticationDTO.getAvatarUrl())) {
            String hash = "0000000000000000000000000000000";

            if (!Util.isEmpty(authenticationDTO.getEmail())) {
                hash = SecurityUtil.getHash(authenticationDTO.getEmail(), SecurityUtil.MD5);
            }
            
            authenticationDTO.setAvatarUrl(String.format(AVATAR_URL_TEMPLATE, hash));
        }
    }

    @Override
    public void logout(String userToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", userToken);

        RestClient.formRequest(getLogoutEndpoint(), HttpMethod.POST, Object.class, null, headers);
    }

    private String getAuthProviderUrl() {
        authProviderUrl = config.getByName(ApplicationConfigEnum.AUTH_PROVIDER_URL);
        return authProviderUrl;
    }

    private String getAuthResourceUrl() {
        authResourceUrl = getAuthProviderUrl() + "/api/admin/auth/user";
        return authResourceUrl;
    }

    private String getLoginEndpoint() {
        loginEndpoint = getAuthResourceUrl() + "/default/login/admin";
        return loginEndpoint;
    }

    private String getLogoutEndpoint() {
        logoutEndpoint = getAuthResourceUrl() + "/default/logout";
        return logoutEndpoint;
    }

    private String getAuthenticatedUserEndpoint() {
        authenticatedUserEndpoint = getAuthResourceUrl() + "/default";
        return authenticatedUserEndpoint;
    }

}
