package br.com.cds.portal.service;

import br.com.cds.connecta.framework.core.controller.AbstractBaseController;
import br.com.cds.portal.service.rest.dto.ApplicationConfiguration;
import br.com.cds.portal.service.rest.dto.Configuration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = "application")
public class ApplicationController extends AbstractBaseController<ApplicationConfiguration> {

    @Override
    protected ApplicationConfiguration get(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<ApplicationConfiguration> list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Configuration config = new Configuration();

        ApplicationConfiguration appPresenter = new ApplicationConfiguration();
        appPresenter.setName("presenter");
        appPresenter.setTitle("Presenter");
        appPresenter.setHost("http://localhost:7001/presenter2-dev");
        config.addApplication(appPresenter);

        ApplicationConfiguration appCollector = new ApplicationConfiguration();
        appCollector.setName("collector");
        appCollector.setTitle("Collector");
        appCollector.setHost("http://localhost:7001/collector2-dev");
        config.addApplication(appCollector);

        ApplicationConfiguration appMaps4 = new ApplicationConfiguration();
        appMaps4.setName("maps4");
        appMaps4.setTitle("Maps4");
        appMaps4.setHost("http://localhost:7001/maps4-dev");
        config.addApplication(appMaps4);

        return config.getApplications();
    }

    @Override
    protected ApplicationConfiguration save(ApplicationConfiguration entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ApplicationConfiguration createWithUpload(MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ApplicationConfiguration update(Long id, ApplicationConfiguration entity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ApplicationConfiguration updateWithUpload(Long id, MultipartHttpServletRequest multipartRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void delete(Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
