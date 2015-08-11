package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.security.SecurityContextUtil;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
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
    
    private @Autowired IApplicationConfigAS config;

    private String authProviderUrl;
    private String userResourceUrl;
    private String createUserEndpoint;
    private String deleteUserEndpoint;
    private String updateUserEndpoint;
    private String updateUserCredentialsEndpoint;

    @Override
    public UserDTO createUser(UserDTO user) {
        RestClient.postForObject(getCreateUserEndpoint(), user, UserDTO.class);
        return user;
    }

    @Override
    public void updatePassword(String userId, UserCredentialsDTO credentials) {
        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUser().getToken());
        
        RestClient.putForObject(getUpdateUserCredentialsEndpoint(), credentials, headers, null, userId);
    }

    @Override
    public void updateUser(UserProfileDTO userProfile) {
        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUser().getToken());
        
        RestClient.putForObject(getUpdateUserEndpoint(), userProfile, headers, null, userProfile.getId());
    }

    @Override
    public void deleteUser(String userId) {
        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUser().getToken());
        
        RestClient.formRequest(getDeleteUserEndpoint(), HttpMethod.DELETE, null, null, headers, userId);
    }
    
      
    private String getAuthProviderUrl() {
        authProviderUrl = config.getByName(ApplicationConfigEnum.AUTH_PROVIDER_URL);
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

    private String getUpdateUserEndpoint() {
        updateUserEndpoint = getUserResourceUrl() + "/{userId}/profile";
        return updateUserEndpoint;
    }
    
}
