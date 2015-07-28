package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.core.domain.security.AuthenticationDTO;
import br.com.cds.connecta.portal.business.applicationService.IAuthenticationAS;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired IAuthenticationAS authAS;
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity authtenticateUser(HttpServletResponse response, String username, String password){
        try {
            AuthenticationDTO auth = authAS.authenticate(username, password);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Set-Cookie","Authentication="+auth.getToken());
            
            return new ResponseEntity(auth, headers, HttpStatus.OK);
        } catch (RestClientException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    
    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
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
}
