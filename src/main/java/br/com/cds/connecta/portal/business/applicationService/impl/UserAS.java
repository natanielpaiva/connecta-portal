package br.com.cds.connecta.portal.business.applicationService.impl;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Role;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.RoleDAO;
import br.com.cds.connecta.portal.persistence.UserDAO;
import br.com.cds.connecta.portal.persistence.specification.RoleSpecification;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
@Service
public class UserAS implements IUserAS {

    @Autowired
    private UserDAO userRepository;
    
    @Autowired
    private RoleDAO roleRepository;
    
//    @Autowired
//    private IDomainAS domainAS;
    
    @Override
    public User get(User user) {
        return userRepository.findByLogin(user.getLogin());
    }

//    @Autowired 
//    private IApplicationConfigAS configAS;
//    
//    @Autowired
//    private IAuthenticationAS authAS;
//    
//    @Autowired
//    private UserDAO userDAO;
//    
//    @Autowired
//    private IApplicationConfigAS config;
//
//    private static final String AVATAR_URL_TEMPLATE = "http://gravatar.com/avatar/%s?s=50&d=mm";
//
//    @Override
//    public AuthenticationDTO saveUser(UserDTO user) throws Exception{
//        prepareToSave(user);
//
//        boolean userExists = checkIfUserExists(user.getProfile().getId());
//
//        if (userExists) {
//            String token = user.getCredentials().getToken();
//            Boolean hasToken = Util.isNotEmpty(token);
//
//            //Caso o usuário possua um token de rede social, autenticar baseado neste token
//            //Caso não, lançar exceção para username já existente.
//            if (hasToken) {
//                return authenticateUser(user);
//            } else {
//                throw new IllegalArgumentException("USER.CREATE.ERROR.EXISTS");
//            }
//        } else {
//            saveOrUpdateWithUpload(user, null);
//            return authenticateUser(user);
//        }
//    }
//
    @Override
    public User saveOrUpdateWithUpload(User user, MultipartFile image) throws IOException {
    	//New User
    	if(Util.isNull(user.getId())){
    		User usr = userRepository.findByLogin(user.getLogin());
    		if(Util.isNotNull(usr)){
    			throw new AlreadyExistsException("Usuário","Login");
    		}
    		if(Util.isNotNull(image)){
    			user.setImage(image.getBytes());
    		}
    		//TODO Remover após definir como será o cadastro de usuario
    		user.setImageUrl("http://icon-icons.com/icons2/35/PNG/512/"
    				+ "caucasian_head_man_person_people_avatar_2859.png");
    		
    		Role roleUsr = roleRepository.findOne(RoleSpecification.byName("ROLE_USER"));
    		user.setRoles(Arrays.asList(roleUsr));
    		
    		return userRepository.save(user);
    	//Update User
    	}else{
    		User userDB = userRepository.findOne(user.getId());
    		userDB.mergePropertiesProfile(user);
    		if(Util.isNotNull(image)){
    			userDB.setImage(image.getBytes());
    		}
    		return userDB;
    	}
    }
    
//
//    /**
//     * Salva o usuário no DB do Portal
//     *
//     * @param image
//     * @param user
//     * @throws IOException
//     */
//    private void saveUserToDatabase(MultipartFile image, User user) throws IOException {
//        if (Util.isNotNull(image)) {
//            user.setImage(image.getBytes());
//        }
//
//        userDAO.saveAndFlush(user);
//    }
//
//    @Override
//    public byte[] getUserImage(String username) {
//        User user = userDAO.findByUsername(username);
//        
//        Hibernate.initialize(user.getImage());
//        
//        return user.getImage();
//    }
//
//    @Override
//    public String generateAvatarUrl(String userId, String email, AuthenticationDTO authDTO) {
//        User user = userDAO.findByUsername(userId);
//
//        if (Util.isNotNull(user.getImage())) {
//            String avatarEndpoint = config.getByName(ApplicationConfigEnum.USER_AVATAR_ENDPOINT);
//            UriComponentsBuilder url = UriComponentsBuilder.fromHttpUrl(avatarEndpoint);
//            UriComponents build = url.buildAndExpand(userId);
//            authDTO.setAvatarUrlType(AvatarUrlType.DATABASE);
//            return build.toUriString();
//        } else if (Util.isNotNull(user) && Util.isNotNull(user.getImageUrl())) {
//            authDTO.setAvatarUrlType(AvatarUrlType.SOCIALNETWORK);
//            return user.getImageUrl();
//        } else {
//            String hash = "0000000000000000000000000000000";
//
//            if (Util.isNotEmpty(email)) {
//                hash = SecurityUtil.getHash(email, SecurityUtil.MD5);
//            }
//            
//            authDTO.setAvatarUrlType(AvatarUrlType.GRAVATAR);
//
//            return String.format(AVATAR_URL_TEMPLATE, hash);
//        }
//    }
//
//    private AuthenticationDTO authenticateUser(UserDTO user) {
//        return authAS.authenticate(user);
//    }
//
//    private void prepareToSave(UserDTO user) {
//        String token = user.getCredentials().getToken();
//        Boolean hasToken = token != null && !token.isEmpty() && Util.isEmpty(user.getCredentials().getPassword());
//
//        if (hasToken && Util.isNotNull(user.getCredentials())) {
//            UserDTO.handleTokenAuth(user);
//        }
//
//    }
//
//    /**
//     * Checa se o usuário existe no camunda
//     * @param userId
//     * @return 
//     */
//    private boolean checkIfUserExists(String userId) {
//        try {
//            RestClient.getForObject(getUserProfileEndpoint(), UserProfileDTO.class, userId);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//    @Override
//    public void updatePassword(String userId, UserCredentialsDTO credentials) {
//        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUserToken());
//
//        RestClient.putForObject(getUpdateUserCredentialsEndpoint(), credentials, headers, null, userId);
//    }
//
//    @Override
//    public void updateUser(UserProfileDTO userProfile) {
//        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUserToken());
//
//        RestClient.putForObject(getUserProfileEndpoint(), userProfile, headers, null, userProfile.getId());
//    }
//
//    @Override
//    public void deleteUser(String userId) {
//        Map<String, String> headers = Collections.singletonMap("Authorization", SecurityContextUtil.getCurrentUserAuthentication().getToken());
//
//        RestClient.formRequest(getDeleteUserEndpoint(), HttpMethod.DELETE, null, null, headers, userId);
//    }
//
//    /**
//     * Executa request para o Camunda salvar o usuário
//     *
//     * @param user
//     */
//    private void executeCamundaSaveRequest(UserDTO user) {
//        RestClient.postForObject(getCreateUserEndpoint(), user, UserDTO.class);
//    }
//
//    private String getAuthProviderUrl() {
//        return configAS.getByName(ApplicationConfigEnum.AUTH_PROVIDER_URL);
//    }
//
//    private String getUserResourceUrl() {
//        return getAuthProviderUrl() + "/api/engine/engine/default/user";
//    }
//
//    private String getCreateUserEndpoint() {
//        return getUserResourceUrl() + "/create";
//    }
//
//    private String getUpdateUserCredentialsEndpoint() {
//        return getUserResourceUrl() + "/{userId}/credentials";
//    }
//
//    private String getDeleteUserEndpoint() {
//        return getUserResourceUrl() + "/{userId}";
//    }
//
//    private String getUserProfileEndpoint() {
//        return getUserResourceUrl() + "/{userId}/profile";
//    };
}
