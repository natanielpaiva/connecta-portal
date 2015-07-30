package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
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

    private @Autowired
    IApplicationConfigAS config;

    private String AUTH_PROVIDER_URL;
    private String AUTH_ENDPOINT;
    private String LOGIN_ENDPOINT;
    private String GET_AUTH_USER_ENDPOINT;
    private String LOGOUT_ENDPOINT;

    @Override
    public AuthenticationDTO authenticate(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("password", password);
        initializeURIs();
        return RestClient.request(LOGIN_ENDPOINT, HttpMethod.POST, AuthenticationDTO.class, params, null);
    }

    @Override
    public AuthenticationDTO getAuthenticatedUser(String userToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", userToken);

        initializeURIs();
        return RestClient.request(GET_AUTH_USER_ENDPOINT, HttpMethod.GET, AuthenticationDTO.class, null, headers);
    }

    @Override
    public void logout(String userToken) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", userToken);

        initializeURIs();
        RestClient.request(LOGOUT_ENDPOINT, HttpMethod.POST, Object.class, null, headers);
    }

    /**
     * Método de inicialização das URLs, 
     * Utilizado devido a ordem de carregamento do DML durante os testes.
     */
    private void initializeURIs() {
        if ((Util.isEmpty(AUTH_PROVIDER_URL))) {
            AUTH_PROVIDER_URL = config.getByName(ApplicationConfigEnum.AUTH_PROVIDER_URL);
            AUTH_ENDPOINT = AUTH_PROVIDER_URL + "/api/admin/auth/user";
            LOGIN_ENDPOINT = AUTH_ENDPOINT + "/default/login/admin";
            GET_AUTH_USER_ENDPOINT = AUTH_ENDPOINT + "/default";
            LOGOUT_ENDPOINT = AUTH_ENDPOINT + "/default/logout";
        }
    }

}
