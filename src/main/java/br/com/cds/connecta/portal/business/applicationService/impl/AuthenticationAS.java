package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
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

    private @Autowired IApplicationConfigAS config;

    private String authProviderUrl;
    private String authResourceUrl;
    private String loginEndpoint;
    private String logoutEndpoint;
    private String authenticatedUserEndpoint;

    @Override
    public AuthenticationDTO authenticate(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        return RestClient.formRequest(getLoginEndpoint(), HttpMethod.POST, AuthenticationDTO.class, params, null);
    }

    @Override
    public AuthenticationDTO getAuthenticatedUser(String userToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", userToken);

        return RestClient.formRequest(getAuthenticatedUserEndpoint(), HttpMethod.GET, AuthenticationDTO.class, null, headers);
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
