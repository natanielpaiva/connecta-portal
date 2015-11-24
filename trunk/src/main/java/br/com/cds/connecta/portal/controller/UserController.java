package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.core.context.HibernateAwareObjectMapper;
import br.com.cds.connecta.framework.core.domain.annotation.PublicResource;
import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.security.SecurityContextUtil;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.domain.security.UserCredentialsDTO;
import br.com.cds.connecta.portal.domain.security.UserDTO;
import br.com.cds.connecta.portal.entity.UserImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Julio Lemes
 * @date Aug 10, 2015
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired IUserAS userAS;
    @Autowired IAuthenticationAS authAS;
    @Autowired HibernateAwareObjectMapper objectMapper;

    @PublicResource
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserDTO user) throws Exception {
        try {
            AuthenticationDTO savedUser = userAS.saveUser(user);
            return new ResponseEntity(savedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        } catch(BusinessException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "profile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateUserProfileWithUpload(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("user") String userDtoStr) throws Exception {
        
        UserDTO user = objectMapper.readValue(userDtoStr, UserDTO.class);
        userAS.saveOrUpdateWithUpload(user, image);
        
        AuthenticationDTO currentUser = SecurityContextUtil.getCurrentUserAuthentication();
        return new ResponseEntity(currentUser, HttpStatus.OK);
        
    }
    
    @PublicResource
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createUserWithUpload(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("user") String userDtoStr) throws Exception {
        
        UserDTO user = objectMapper.readValue(userDtoStr, UserDTO.class);
        try {
            userAS.saveOrUpdateWithUpload(user, image);
        } catch(BusinessException exception) {
            return new ResponseEntity(exception.getMessageError().getKey(), HttpStatus.CONFLICT);
        }
        
        return new ResponseEntity(authAS.authenticate(user), HttpStatus.CREATED);
        
    }

    @RequestMapping(value = "credentials", method = RequestMethod.PUT)
    public ResponseEntity changePassword(@RequestBody UserCredentialsDTO credentials) {
        String userId = SecurityContextUtil.getCurrentUserId();
        try {
            userAS.updatePassword(userId, credentials);
        } catch (HttpStatusCodeException ex) {
            return new ResponseEntity(ex.getResponseBodyAsString() ,ex.getStatusCode());
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PublicResource
    @RequestMapping(value = "{username}/avatar", method = RequestMethod.GET, 
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity getUserAvatar(@PathVariable("username") String username){
        UserImage image = userAS.getUserImage(username);
        if (Util.isNotNull(image)) {
            return new ResponseEntity(image.getImage(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "{username}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String username) {
        userAS.deleteUser(username);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
