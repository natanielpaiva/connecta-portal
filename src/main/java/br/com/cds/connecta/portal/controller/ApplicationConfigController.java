package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.entity.ApplicationConfig;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Heloisa
 */
@RestController
@RequestMapping("application-config")
public class ApplicationConfigController {

    @Autowired
    private IApplicationConfigAS configAS;

    @RequestMapping(value = "{param}/logo",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationConfig> upload(@PathVariable("param") String param,
            @RequestParam(value = "logo") MultipartFile logo) throws IOException {

        ApplicationConfig application = configAS.uploadLogo(param, logo);
        return new ResponseEntity(application, HttpStatus.OK);
    }

    @RequestMapping(value = "{param}/logo.png",
            method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public void getLogo(HttpServletResponse response,
            @PathVariable("param") String param) throws IOException {

        InputStream is = configAS.getLogoByParam(param);

        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", param + ".png");

        response.setHeader(headerKey, headerValue);

        IOUtils.copy(is, response.getOutputStream());
        response.flushBuffer();
    }

}