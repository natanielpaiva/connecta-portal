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
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.entity.UserImage;
import br.com.cds.connecta.portal.persistence.UserDAO;
import br.com.cds.connecta.portal.persistence.UserImageDAO;
import br.com.cds.connecta.portal.security.authentication.token.strategy.TokenVerifierStrategy;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
@Service
public class UserAS implements IUserAS {

    @Autowired IApplicationConfigAS configAS;
    @Autowired IAuthenticationAS authAS;
    @Autowired TokenVerifierStrategy tokenVerifierStrategy;
    @Autowired UserDAO userDAO;
    @Autowired UserImageDAO userImageDAO;

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

        if (userExists) {
            String token = user.getCredentials().getToken();
            Boolean hasToken = Util.isNotEmpty(token);

            //Caso o usuário possua um token de rede social, autenticar baseado neste token
            //Caso não, lançar exceção para username já existente.
            if (hasToken) {
                return authenticateUser(user);
            } else {
                throw new IllegalArgumentException("USER.CREATE.ERROR.EXISTS");
            }
        } else {
            executeCamundaSaveRequest(user);

            //TODO Recuperar imagem do usuário a partir da rede social caso seja token
            userDAO.save(user.createUserEntity());
            return authenticateUser(user);
        }
    }

    @Override
    public UserDTO createOrUpdateWithUpload(UserDTO userDTO, MultipartFile image) throws IOException {
        User user = userDTO.createUserEntity();
        boolean userExistsCamunda = checkIfUserExists(userDTO.getProfile().getId());

        if (!userExistsCamunda) {
            executeCamundaSaveRequest(userDTO);
            saveUserToDatabase(image, user);
            
        } else {
            //Verifica se o usuário está logado ou não (criação/update)
            AuthenticationDTO currentUser = SecurityContextUtil.getCurrentUserAuthentication();
            if(Util.isNull(currentUser) || !currentUser.getUserId().equals(userDTO.getProfile().getId())){
                //Se não estiver logado, está tentando criar usuário com username que ja está cadastrado no camunda
                throw new BusinessException(ExceptionEnum.FORBIDDEN, "USER.ERROR.USERNAME_EXISTS");
            }
            
            //Executa o update do Usuário no Camunda
            updateUser(userDTO.getProfile());
            
            //Checar se usuário existe no banco, se não, criar...
            User findUser = userDAO.findByLogin(userDTO.getProfile().getId());
            
            if(Util.isNotNull(findUser)){
                //Atualiza a imagem do usuário caso exista
                if (Util.isNotNull(image)) {
                    UserImage userImage = findUser.getImage();
                    if(Util.isNotNull(userImage)){
                        setImage(image, userImage);
                    }
                }
            } else {
                saveUserToDatabase(image, user);
            }
            
        }

        return userDTO;
    }

    
    /**
     * Salva o usuário no DB do Portal
     * @param image
     * @param user
     * @throws IOException 
     */
    private void saveUserToDatabase(MultipartFile image, User user) throws IOException {
        if (Util.isNotNull(image)) {
            UserImage userImage = new UserImage();
            setImage(image, userImage);
            user.setImage(userImage);
        }
        
        userDAO.save(user);
    }

    private void setImage(MultipartFile image, UserImage userImage) throws IOException {
        if(Util.isNotEmpty(image.getOriginalFilename())){
            userImage.setName(image.getOriginalFilename());
        }
        
        userImage.setImage(image.getBytes());
    }
    
    private AuthenticationDTO authenticateUser(UserDTO user) {
        return authAS.authenticate(user);
    }

    private void prepareToSave(UserDTO user) {
        String token = user.getCredentials().getToken();
        Boolean hasToken = token != null && !token.isEmpty() && Util.isEmpty(user.getCredentials().getPassword());

        if (hasToken && Util.isNotNull(user.getCredentials())) {
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
    
    /**
     * Executa request para o Camunda salvar o usuário
     * @param user 
     */
    private void executeCamundaSaveRequest(UserDTO user) {
        RestClient.postForObject(getCreateUserEndpoint(), user, UserDTO.class);
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
