package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.AvatarUrlType;
import br.com.cds.connecta.framework.core.domain.MessageEnum;
import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.http.RestClient;
import br.com.cds.connecta.framework.core.security.SecurityContextUtil;
import br.com.cds.connecta.framework.core.util.SecurityUtil;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

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
    @Autowired IApplicationConfigAS config;

    private static final String AVATAR_URL_TEMPLATE = "http://gravatar.com/avatar/%s?s=50&d=mm";

    @Override
    public AuthenticationDTO saveUser(UserDTO user) throws Exception{
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
            saveOrUpdateWithUpload(user, null);
            return authenticateUser(user);
        }
    }

    @Override
    public UserDTO saveOrUpdateWithUpload(UserDTO userDTO, MultipartFile image) throws IOException {
        User user = userDTO.createUserEntity();
        boolean userExistsCamunda = checkIfUserExists(userDTO.getProfile().getId());

        if (!userExistsCamunda) {
            executeCamundaSaveRequest(userDTO);
            saveUserToDatabase(image, user);

        } else {
            //Verifica se o usuário está logado ou não (criação/update)
            AuthenticationDTO currentUser = SecurityContextUtil.getCurrentUserAuthentication();
            if (Util.isNull(currentUser)) {
                //Se não estiver logado, está tentando criar usuário com username que ja está cadastrado no camunda
                throw new BusinessException(MessageEnum.FORBIDDEN, "USER.ERROR.USERNAME_EXISTS");
            } else if (!currentUser.getUserId().equals(userDTO.getProfile().getId())) {
                //Se o username do user logado não bate com o user da requisição, shit just hit the fan
                throw new BusinessException(MessageEnum.REJECTED, "USER.ERROR.USERNAME_MISMATCH");
            }

            //Executa o update do Usuário no Camunda
            updateUser(userDTO.getProfile());

            //Checar se usuário existe no banco, se não, criar...
            User findUser = userDAO.findByLogin(userDTO.getProfile().getId());

            if (Util.isNotNull(findUser)) {
                UserImage userImage = findUser.getImage();
                if (Util.isNotNull(image)) {
                    //Atualiza a imagem do usuário caso exista
                    if (Util.isNull(userImage)) {
                        userImage = new UserImage();
                    }

                    setImage(image, userImage);
                    findUser.setImage(userImage);
                    userDAO.save(findUser);
                } else if (Util.isNull(userDTO.getProfile().getAvatarUrl()) && Util.isNotNull(userImage)) {
                    //Usuário remove a imagem no form do profile e possui uma imagem no banco
                    //Remover imagem no banco
                    findUser.setImage(null);
                    userDAO.save(findUser);
                    userImageDAO.delete(userImage.getId());
                }

            } else {
                saveUserToDatabase(image, user);
            }

        }

        return userDTO;
    }

    /**
     * Salva o usuário no DB do Portal
     *
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

        userDAO.saveAndFlush(user);
    }

    private void setImage(MultipartFile image, UserImage userImage) throws IOException {
        if (Util.isNotEmpty(image.getOriginalFilename())) {
            userImage.setName(image.getOriginalFilename());
        }

        userImage.setImage(image.getBytes());
    }

    @Override
    public UserImage getUserImage(String username) {
        return userImageDAO.findByUserLogin(username);
    }

    @Override
    public String generateAvatarUrl(String userId, String email, AuthenticationDTO authDTO) {
        UserImage userImage = userImageDAO.findByUserLogin(userId);
        User user = userDAO.findByLogin(userId);

        if (Util.isNotNull(userImage)) {
            String avatarEndpoint = config.getByName(ApplicationConfigEnum.USER_AVATAR_ENDPOINT);
            UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(avatarEndpoint);
            UriComponents build = url.buildAndExpand(userId);
            authDTO.setAvatarUrlType(AvatarUrlType.DATABASE);
            return build.toUriString();
        } else if (Util.isNotNull(user) && Util.isNotNull(user.getImageUrl())) {
            authDTO.setAvatarUrlType(AvatarUrlType.SOCIALNETWORK);
            return user.getImageUrl();
        } else {
            String hash = "0000000000000000000000000000000";

            if (Util.isNotEmpty(email)) {
                hash = SecurityUtil.getHash(email, SecurityUtil.MD5);
            }
            
            authDTO.setAvatarUrlType(AvatarUrlType.GRAVATAR);

            return String.format(AVATAR_URL_TEMPLATE, hash);
        }
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

    /**
     * Checa se o usuário existe no camunda
     * @param userId
     * @return 
     */
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
     *
     * @param user
     */
    private void executeCamundaSaveRequest(UserDTO user) {
        RestClient.postForObject(getCreateUserEndpoint(), user, UserDTO.class);
    }

    private String getAuthProviderUrl() {
        return configAS.getByName(ApplicationConfigEnum.AUTH_PROVIDER_URL);
    }

    private String getUserResourceUrl() {
        return getAuthProviderUrl() + "/api/engine/engine/default/user";
    }

    private String getCreateUserEndpoint() {
        return getUserResourceUrl() + "/create";
    }

    private String getUpdateUserCredentialsEndpoint() {
        return getUserResourceUrl() + "/{userId}/credentials";
    }

    private String getDeleteUserEndpoint() {
        return getUserResourceUrl() + "/{userId}";
    }

    private String getUserProfileEndpoint() {
        return getUserResourceUrl() + "/{userId}/profile";
    }
}
