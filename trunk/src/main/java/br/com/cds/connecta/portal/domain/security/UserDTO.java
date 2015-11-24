package br.com.cds.connecta.portal.domain.security;

import br.com.cds.connecta.framework.core.util.SecurityUtil;
import br.com.cds.connecta.portal.entity.User;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
public class UserDTO {

    protected UserProfileDTO profile;
    protected UserCredentialsDTO credentials;

    
    /**
     * Verifica os dados de criação do User e aplica as RN's requeridas 
     * para a criação de um novo User
     *
     * @param user
     * @return
     */
    public static UserDTO handleTokenAuth(UserDTO user) {
        UserProfileDTO profile = user.getProfile();
        profile.setId(profile.getEmail());

        String password = SecurityUtil.getConnectaPasswordBase64Hash(profile.getEmail());
        user.getCredentials().setPassword(password);

        return user;
    }
    
    public User createUserEntity(){
        User user = new User();
        user.setLogin(this.getProfile().getId());
        user.setImageUrl(this.getProfile().getAvatarUrl());
        return user;
    }

    public UserProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(UserProfileDTO profile) {
        this.profile = profile;
    }

    public UserCredentialsDTO getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentialsDTO credentials) {
        this.credentials = credentials;
    }

}
