package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.domain.MessageEnum;
import static br.com.cds.connecta.framework.core.util.Util.isNull;
import static br.com.cds.connecta.framework.core.util.Util.isNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Role;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.RoleDAO;
import br.com.cds.connecta.portal.persistence.UserRepository;
import br.com.cds.connecta.portal.persistence.specification.RoleSpecification;
import br.com.cds.connecta.portal.security.UserRepositoryUserDetails;
import java.security.Principal;
import java.util.List;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 *
 * @author <heloisa.morais@cds.com.br>
 */
@Service
public class UserAS implements IUserAS {

    private final static String DEFAULT_USER_IMAGE = "./user-default.jpg";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleDAO roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User get(Long id) {
        User user = userRepository.findOne(id);

        if (isNull(user)) {
            throw new ResourceNotFoundException(User.class.getCanonicalName());
        }

        return user;
    }

    @Override
    public User get(Principal user) {
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) user;
        UserRepositoryUserDetails repositoryUserDetails
                = (UserRepositoryUserDetails) auth2Authentication.getPrincipal();
        
        return getByEmail(repositoryUserDetails.getUser().getEmail());
    }

    @Override
    public List<User> getByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getByEmail(String username) {
        User user = userRepository.findByEmail(username);

        if (isNull(user)) {
            throw new ResourceNotFoundException(User.class.getCanonicalName());
        }

        return user;
    }
    
    @Override
    public boolean isAvailableEmail(String email){
        User user = userRepository.findByEmail(email);
        
        if(isNotNull(user)){
            throw new AlreadyExistsException(User.class.getSimpleName(), "email");
        }
        
        return true;
    }

    @Override
    public InputStream getUserImage(Long id) throws IOException {
        User user = get(id);

        InputStream is;

        if (isNull(user.getImage())) {
            is = getClass().getClassLoader().getResourceAsStream(DEFAULT_USER_IMAGE);
        } else {
            Hibernate.initialize(user.getImage());
            is = new ByteArrayInputStream(user.getImage());
        }

        return is;
    }

    @Override
    public void setUserImage(Long id) throws IOException {
        User user = get(id);
        user.setImage(null);
    }

    @Override
    public User upload(Long id, MultipartFile file) throws IOException {
        User user = get(id);

        if (isNull(file)) {
            user.setImage(null);
        } else {
            user.setImage(file.getBytes());
        }

        return userRepository.save(user);
    }

    @Override
    public User save(User user) {
        User usr = userRepository.findByEmail(user.getEmail());

        if (Util.isNotNull(usr)) {
            throw new AlreadyExistsException("Usuário", "E-mail");
        }

        Role roleUsr = roleRepository.findOne(RoleSpecification.byName("ROLE_USER"));
        user.setRoles(Arrays.asList(roleUsr));

        user.setImage(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        User userFromDatabase = get(id); // Somente para verificar se já existe

        user.setId(id); // O ID da URL prevalece
        user.setImage(userFromDatabase.getImage()); // Imagem continua a anterior
        user.setPassword(userFromDatabase.getPassword()); // Senha continua a anterior
        user.setRoles(userFromDatabase.getRoles()); // Roles também
        user.setDomains(userFromDatabase.getDomains()); // E Domínios também :)

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updatePassword(User userLogged, String oldPass, String newPass) {
        if (!passwordEncoder.matches(oldPass, userLogged.getPassword())) {
            throw new BusinessException(MessageEnum.INTEGRITY_ERROR);
        }
        userLogged.setPassword(passwordEncoder.encode(newPass));
        return userRepository.save(userLogged);
    }

//    @Override
//    public User update(User user, MultipartFile image) throws IOException {
//        //New User
//        if (Util.isNull(user.getId())) {
//            User usr = userRepository.findByLogin(user.getLogin());
//
//            if (Util.isNotNull(usr)) {
//                throw new AlreadyExistsException("Usuário", "Login");
//            }
//            if (Util.isNotNull(image)) {
//                user.setImage(image.getBytes());
//            }
//
//            Role roleUsr = roleRepository.findOne(RoleSpecification.byName("ROLE_USER"));
//            user.setRoles(Arrays.asList(roleUsr));
//
//            return userRepository.save(user);
//            //Update User
//        } else {
//            User userDB = userRepository.findOne(user.getId());
//            userDB.mergePropertiesProfile(user);
//            if (Util.isNotNull(image)) {
//                userDB.setImage(image.getBytes());
//            }
//            return userDB;
//        }
//    }
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
