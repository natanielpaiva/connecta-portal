package br.com.cds.connecta.portal.controller;

import br.com.cds.connecta.framework.core.controller.AbstractBaseController;
import br.com.cds.connecta.portal.business.applicationService.IApplicationAS;
import br.com.cds.connecta.portal.entity.Application;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("application")
public class ApplicationController extends AbstractBaseController<Application> {
    
    @Autowired
    private IApplicationAS service;

    @Override
    protected ResponseEntity<Application> get(Long id, HttpServletRequest request, HttpServletResponse response) {
        Application entity = service.get(id);
        return new ResponseEntity<Application>(entity, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<List<Application>> list(HttpServletRequest request, HttpServletResponse response) {
        List<Application> list = service.list();
        return new ResponseEntity<List<Application>>(list, HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Application> save(Application application, HttpServletRequest request, HttpServletResponse response) {
        Application entity = service.save(application);
        return new ResponseEntity<Application>(entity, HttpStatus.CREATED);
    }
    
    @Override
    protected ResponseEntity<Application> update(Long id, Application entity, HttpServletRequest request, HttpServletResponse response) {
        Application updatedEntity = service.update(entity);
        return new ResponseEntity<Application>(updatedEntity, HttpStatus.OK);
    }

    @Override
    protected void delete(Long id, HttpServletRequest request, HttpServletResponse response) {
        service.delete(id);
    }
    @Override
    protected ResponseEntity<Application> createWithUpload(MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ResponseEntity<Application> updateWithUpload(Long id, MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
