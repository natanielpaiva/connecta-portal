package br.com.cds.portal.controller;

import br.com.cds.connecta.framework.core.controller.AbstractBaseController;
import br.com.cds.portal.dao.ApplicationDAO;
import br.com.cds.portal.entity.Application;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "application")
public class ApplicationController extends AbstractBaseController<Application> {
    
    @Autowired
    private ApplicationDAO dao;

    @Override
    protected Application get(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return dao.get(id);
    }

    @Override
    protected List<Application> list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return dao.list();
    }

    @Override
    protected Application save(Application entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Application createWithUpload(MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Application update(Long id, Application entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Application updateWithUpload(Long id, MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void delete(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
