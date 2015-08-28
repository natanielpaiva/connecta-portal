package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.core.context.HibernateAwareObjectMapper;
import br.com.cds.connecta.framework.core.domain.annotation.PublicResource;
import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.framework.core.security.SecurityContextUtil;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.domain.security.UserCredentialsDTO;
import br.com.cds.connecta.portal.domain.security.UserDTO;
import br.com.cds.connecta.portal.domain.security.UserProfileDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    @Autowired HibernateAwareObjectMapper objectMapper;

    @PublicResource
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserDTO user) {
        try {
            AuthenticationDTO savedUser = userAS.createUser(user);
            return new ResponseEntity(savedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
        } catch(BusinessException ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createUserWithUpload(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("user") String userProfileStr) throws Exception {
        
        UserDTO user = objectMapper.readValue(userProfileStr, UserDTO.class);
//        userAS.createOrUpdateWithUpload(user, image);
        
//        userAS.updateUser(userProfile);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "password", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody UserCredentialsDTO credentials) {
        String userId = SecurityContextUtil.getCurrentUserId();
        userAS.updatePassword(userId, credentials);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable String userId) {
        userAS.deleteUser(userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
