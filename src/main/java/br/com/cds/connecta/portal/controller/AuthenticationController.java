package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.core.context.ConnectaSpringContext;
import br.com.cds.connecta.framework.core.domain.annotation.PublicResource;
import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.domain.security.UserDTO;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

/**
 *
 * @author Julio Lemes
 * @date Jul 24, 2015
 */
@RestController
@PublicResource
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired IAuthenticationAS authAS;
    @Autowired IUserAS userAS;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity authenticateUser(HttpServletResponse response, @RequestBody UserDTO user){
        try {
            AuthenticationDTO auth = authAS.authenticate(user);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie","X-Authorization-Token="+auth.getToken());
            
            return new ResponseEntity(auth, headers, HttpStatus.OK);
        } catch (RestClientException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "{token}", method = RequestMethod.GET)
    public ResponseEntity getAuthenticatedUser(@PathVariable("token") String userToken){
        try {
            AuthenticationDTO auth = authAS.getAuthenticatedUser(userToken);
            return new ResponseEntity(auth, HttpStatus.OK);
        } catch (RestClientException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    public ResponseEntity logout(@RequestParam("token") String userToken){
        authAS.logout(userToken);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public ResponseEntity test(){
        IApplicationConfigAS config = ConnectaSpringContext.getBean(IApplicationConfigAS.class);
        String conf = config.getByName(ApplicationConfigEnum.FACEBOOK_VERIFY_TOKEN_URL);
        return new ResponseEntity(conf, HttpStatus.OK);
    }
}
