package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.ExceptionEnum;
import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.security.SecurityContextUtil;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.domain.security.UserCredentialsDTO;
import br.com.cds.connecta.portal.domain.security.UserDTO;
import br.com.cds.connecta.portal.domain.security.UserProfileDTO;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
@Service
public class UserAS implements IUserAS {
    
    private @Autowired IApplicationConfigAS configAS;
    private @Autowired IAuthenticationAS authAS;

    private String authProviderUrl;
    private String userResourceUrl;
    private String createUserEndpoint;
    private String deleteUserEndpoint;
    private String userProfileEndpoint;
    private String updateUserCredentialsEndpoint;

    @Override
    public AuthenticationDTO createUser(UserDTO user) {
        prepareToSave(user);
        
        boolean userExists = checkIfUserExists(user.getProfile().getId());
        
        if(userExists){
            String token = user.getCredentials().getToken();
            Boolean hasToken = token != null && !token.isEmpty() && Util.isEmpty(user.getCredentials().getPassword());
            
            //Caso o usuário possua um token de rede social, autenticar baseado neste token
            //Caso não, lançar exceção para username já existente.
            if(hasToken){
                return authenticateUser(user);
            } else {
                throw new IllegalArgumentException("USER.CREATE.ERROR.EXISTS");
            }
        } else {
            //Executa request para o Camunda salvar o usuário
            RestClient.postForObject(getCreateUserEndpoint(), user, UserDTO.class);
            return authenticateUser(user);
        }
    }

    private AuthenticationDTO authenticateUser(UserDTO user) {
        AuthenticationDTO auth = authAS.authenticate(user.getProfile().getId(), user.getCredentials().getPassword());
        return auth;
    }
    
    private void prepareToSave(UserDTO user){
        String token = user.getCredentials().getToken();
        Boolean hasToken = token != null && !token.isEmpty() && Util.isEmpty(user.getCredentials().getPassword());
        
        if (hasToken && Util.isNull(user.getCredentials())) {
            UserDTO.handleTokenAuth(user);
        }
        
    }

    private boolean checkIfUserExists(String userId) {
        try {
            RestClient.getForObject(getUserProfileEndpoint(), UserProfileDTO.class, userId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void updatePassword(String userId, UserCredentialsDTO credentials) {
        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUserToken());
        
        RestClient.putForObject(getUpdateUserCredentialsEndpoint(), credentials, headers, null, userId);
    }

    @Override
    public void updateUser(UserProfileDTO userProfile) {
        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUserToken());
        
        RestClient.putForObject(getUserProfileEndpoint(), userProfile, headers, null, userProfile.getId());
    }

    @Override
    public void deleteUser(String userId) {
        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUserAuthentication().getToken());
        
        RestClient.formRequest(getDeleteUserEndpoint(), HttpMethod.DELETE, null, null, headers, userId);
    }
    
      
    private String getAuthProviderUrl() {
        authProviderUrl = configAS.getByName(ApplicationConfigEnum.AUTH_PROVIDER_URL);
        return authProviderUrl;
    }
    
    private String getUserResourceUrl() {
        userResourceUrl = getAuthProviderUrl() + "/api/engine/engine/default/user";
        return userResourceUrl;
    }

    private String getCreateUserEndpoint() {
        createUserEndpoint = getUserResourceUrl() + "/create";
        return createUserEndpoint;
    }

    private String getUpdateUserCredentialsEndpoint() {
        updateUserCredentialsEndpoint = getUserResourceUrl() + "/{userId}/credentials";
        return updateUserCredentialsEndpoint;
    }
    
    private String getDeleteUserEndpoint() {
        deleteUserEndpoint = getUserResourceUrl() + "/{userId}";
        return deleteUserEndpoint;
    }

    private String getUserProfileEndpoint() {
        userProfileEndpoint = getUserResourceUrl() + "/{userId}/profile";
        return userProfileEndpoint;
    }
}
