package br.com.cds.portal.service;

import br.com.cds.framework.core.services.template.AbstractRestService;
import br.com.cds.portal.service.rest.dto.ConfigApplicationDTO;
import br.com.cds.portal.service.rest.dto.ConfigDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "config.json")
public class ConfigService extends AbstractRestService {

    /**
     * 
     * @return 
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody ConfigDTO get() {
        ConfigDTO config = new ConfigDTO();

        ConfigApplicationDTO appPresenter = new ConfigApplicationDTO();
        appPresenter.setName("presenter");
        appPresenter.setTitle("Presenter");
        appPresenter.setHost("http://localhost:7001/presenter2-dev");
        config.addApplication(appPresenter);

        ConfigApplicationDTO appCollector = new ConfigApplicationDTO();
        appCollector.setName("collector");
        appCollector.setTitle("Collector");
        appCollector.setHost("http://localhost:7001/collector2-dev");
        config.addApplication(appCollector);

        ConfigApplicationDTO appMaps4 = new ConfigApplicationDTO();
        appMaps4.setName("maps4");
        appMaps4.setTitle("Maps4");
        appMaps4.setHost("http://localhost:7001/maps4-dev");
        config.addApplication(appMaps4);

        return config;
    }
}
