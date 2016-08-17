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
import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.business.applicationService.impl.UserAS;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.security.UserRepositoryUserDetails;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

        OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;

        UserRepositoryUserDetails repositoryUserDetails
                = (UserRepositoryUserDetails) auth2Authentication.getPrincipal();

        User user = userService.getByEmail(repositoryUserDetails.getUser().getEmail());
        user.setPassword(null);

        return user;
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PublicResource
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user) throws Exception {

        user = userService.save(user);

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

    @RequestMapping(value = "validarToken", method = RequestMethod.GET)
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
