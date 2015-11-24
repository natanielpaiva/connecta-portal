package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.entity.ApplicationConfig;
import br.com.cds.connecta.portal.persistence.ApplicationConfigDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Julio Lemes
 * @date Jul 27, 2015
 */
@Service
public class ApplicationConfigAS implements IApplicationConfigAS {

    @Autowired ApplicationConfigDAO dao;
    
    @Override
    public String getByName(ApplicationConfigEnum config) {
        ApplicationConfig appConfig = dao.findByName(config.name());
        return appConfig.getValue();
    }
    
}
