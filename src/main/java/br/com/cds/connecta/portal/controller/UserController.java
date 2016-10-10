package br.com.cds.connecta.portal.controller;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.cds.connecta.framework.core.context.HibernateAwareObjectMapper;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.User;

/**
 * 
 * @author heloisa
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserAS userService;

    @Autowired
    private HibernateAwareObjectMapper objectMapper;

    @Autowired
    private ResourceServerTokenServices tokenServices;

    @RequestMapping("current")
    public User user(Principal principal) {
        User user = userService.get(principal);
        user.setPassword(null);
        return user;
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PublicResource
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) throws Exception {

        user = userService.saveUser(user);

        return new ResponseEntity(user, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "invited", method = RequestMethod.POST)
    public ResponseEntity createInvited(@RequestBody User user) throws Exception {

        user = userService.saveInvited(user);

        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @RequestMapping(value = "{id}/profile.png", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void get(@PathVariable Long id,
            HttpServletResponse response) throws IOException {
        InputStream inputStream = userService.getUserImage(id);

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", id.toString() + ".png");

        response.setHeader(headerKey, headerValue);

        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getByEmail(@RequestParam String email) {
        userService.getByEmail(email);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "hash", method = RequestMethod.GET)
    public ResponseEntity getByHash(@RequestParam String hash) {
        User user = userService.getByHash(hash);

        return new ResponseEntity(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "mail", method = RequestMethod.GET)
    public ResponseEntity isAvailableEmail(@RequestParam String email) {
        userService.isAvailableEmail(email);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "token/validate", method = RequestMethod.GET)
    public ResponseEntity validarToken(@RequestHeader("Authorization") String param) {
        String[] tk = param.split(" ");

        OAuth2AccessToken token = tokenServices.readAccessToken(tk[1]);

        if (token == null || token.isExpired()) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody User user, Principal userLogged) {

        userService.update(id, user);

        return new ResponseEntity(user, HttpStatus.OK);
    }

    @RequestMapping(value = "credentials", method = RequestMethod.POST)
    public ResponseEntity update(@RequestParam("oldPass") String oldPass,
                                 @RequestParam("newPass") String newPass,
                                 Principal userLogged) {

        User user = userService.get(userLogged);
        
        User userUpdated = userService.updatePassword(user, oldPass, newPass);
        return new ResponseEntity(userUpdated, HttpStatus.OK);
    }

    @RequestMapping(value = "{id}/avatar",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity upload(
            @RequestParam(value = "file", required = false) MultipartFile image,
            @PathVariable("id") Long id, Principal principal) throws Exception {

        userService.upload(id, image);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
