package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.security.UserRepositoryUserDetails;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private IUserAS userAS;
    
    @RequestMapping("current")
    public User user(Principal principal) {
        
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
        
        UserRepositoryUserDetails repositoryUserDetails = 
                (UserRepositoryUserDetails) auth2Authentication.getPrincipal();
        
        User user = repositoryUserDetails.getUser();
        user.setPassword(null);
        
        return user;
    }
    
    @RequestMapping( name = "/" , method = RequestMethod.GET)
    public User get(User user){
        user = userAS.get(user);
        user.setPassword(null);
        return user;
    }
    
    

//    @Autowired
//    private IUserAS userAS;
//    
//    @Autowired
//    private IAuthenticationAS authAS;
//    
//    @Autowired
//    private HibernateAwareObjectMapper objectMapper;
//
//    @PublicResource
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity createUser(@RequestBody UserDTO user) throws Exception {
//        try {
//            AuthenticationDTO savedUser = userAS.saveUser(user);
//            return new ResponseEntity(savedUser, HttpStatus.OK);
//        } catch (IllegalArgumentException ex) {
//            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
//        } catch(BusinessException ex){
//            return new ResponseEntity(ex.getMessage(), HttpStatus.FORBIDDEN);
//        }
//    }
//
//    @RequestMapping(value = "profile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity updateUserProfileWithUpload(
//            @RequestParam(value = "image", required = false) MultipartFile image,
//            @RequestParam("user") String userDtoStr) throws Exception {
//        
//        UserDTO user = objectMapper.readValue(userDtoStr, UserDTO.class);
//        userAS.saveOrUpdateWithUpload(user, image);
//        
//        AuthenticationDTO currentUser = SecurityContextUtil.getCurrentUserAuthentication();
//        return new ResponseEntity(currentUser, HttpStatus.OK);
//        
//    }
//    
//    @PublicResource
//    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity createUserWithUpload(
//            @RequestParam(value = "image", required = false) MultipartFile image,
//            @RequestParam("user") String userDtoStr) throws Exception {
//        
//        UserDTO user = objectMapper.readValue(userDtoStr, UserDTO.class);
//        try {
//            userAS.saveOrUpdateWithUpload(user, image);
//        } catch(BusinessException exception) {
//            return new ResponseEntity(exception.getMessageError().getKey(), HttpStatus.CONFLICT);
//        }
//        
//        return new ResponseEntity(authAS.authenticate(user), HttpStatus.CREATED);
//        
//    }
//
//    @RequestMapping(value = "credentials", method = RequestMethod.PUT)
//    public ResponseEntity changePassword(@RequestBody UserCredentialsDTO credentials) {
//        String userId = SecurityContextUtil.getCurrentUserId();
//        try {
//            userAS.updatePassword(userId, credentials);
//        } catch (HttpStatusCodeException ex) {
//            return new ResponseEntity(ex.getResponseBodyAsString() ,ex.getStatusCode());
//        }
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//    
//    @PublicResource
//    @RequestMapping(value = "{username}/avatar", method = RequestMethod.GET, 
//            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
//    public ResponseEntity getUserAvatar(@PathVariable("username") String username){
//        byte[] image = userAS.getUserImage(username);
//        if (Util.isNotNull(image)) {
//            return new ResponseEntity(image, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.NO_CONTENT);
//        }
//    }
//
//    @RequestMapping(value = "{username}", method = RequestMethod.DELETE)
//    public ResponseEntity deleteUser(@PathVariable String username) {
//        userAS.deleteUser(username);
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
}
