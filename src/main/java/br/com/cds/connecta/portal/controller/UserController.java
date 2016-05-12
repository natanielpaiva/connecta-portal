package br.com.cds.connecta.portal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cds.connecta.framework.core.context.HibernateAwareObjectMapper;
import br.com.cds.connecta.framework.core.domain.annotation.PublicResource;
import br.com.cds.connecta.framework.core.exception.BusinessException;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.security.UserRepositoryUserDetails;

@RestController
@RequestMapping("user")
public class UserController {
    
    @Autowired
    private IUserAS userAS;
    
    @Autowired
    private HibernateAwareObjectMapper objectMapper;
    
    @Autowired
    private ResourceServerTokenServices tokenServices;
    
    @RequestMapping("current")
    public User user(Principal principal) {
        
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
        
        UserRepositoryUserDetails repositoryUserDetails = 
                (UserRepositoryUserDetails) auth2Authentication.getPrincipal();
        
        User user = userAS.get(repositoryUserDetails.getUser());
        user.setPassword(null);
        
        return user;
    }
    
    @RequestMapping( name = "/" , method = RequestMethod.GET)
    public User get(User user){
        user = userAS.get(user);
        user.setPassword(null);
        return user;
    }
    
    @RequestMapping(value = "validarToken" , method = RequestMethod.GET)
    public ResponseEntity validarToken(@RequestHeader("Authorization") String param){
    	String[] tk = param.split(" ");
    	
    	OAuth2AccessToken token = tokenServices.readAccessToken(tk[1]);
    	
    	if(token == null || token.isExpired()){
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "profile", 
    		method = RequestMethod.POST, 
    		consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateUserProfileWithUpload(
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam("user") String userDtoStr, Principal userLogged) throws Exception {
        
        User user = objectMapper.readValue(userDtoStr, User.class);
        userAS.saveOrUpdateWithUpload(user, image);
        
        return new ResponseEntity(user, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@PublicResource
	@RequestMapping(value = "create", 
	method = RequestMethod.POST, 
	consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity createUserWithUpload(
			@RequestParam(value = "image", required = false) MultipartFile image,
			@RequestParam("user") String userStr) throws Exception {

		User user = objectMapper.readValue(userStr, User.class);

		try {
			user = userAS.saveOrUpdateWithUpload(user, image);
		} catch (BusinessException exception) {
			return new ResponseEntity(exception.getMessageError().getKey(), HttpStatus.CONFLICT);
		}

		return new ResponseEntity(user, HttpStatus.CREATED);
	}
}
