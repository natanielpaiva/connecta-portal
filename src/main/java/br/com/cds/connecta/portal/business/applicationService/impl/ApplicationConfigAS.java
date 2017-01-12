package br.com.cds.connecta.portal.business.applicationService.impl;

import static br.com.cds.connecta.framework.core.util.Util.isNotNull;
import static br.com.cds.connecta.framework.core.util.Util.isNull;
import br.com.cds.connecta.portal.business.applicationService.IApplicationConfigAS;
import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.entity.ApplicationConfig;
import br.com.cds.connecta.portal.persistence.ApplicationConfigRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Julio Lemes
 * @author Heloisa 
 * @date Jul 27, 2015
 */
@Service
public class ApplicationConfigAS implements IApplicationConfigAS {

    @Autowired
    private ApplicationConfigRepository configRepository;

    @Override
    public String getByName(ApplicationConfigEnum config) {
        ApplicationConfig appConfig = configRepository.findByParam(config.name());
        return appConfig.getValue();
    }

    @Override
    public ApplicationConfig uploadLogo(String param, MultipartFile logo) throws IOException {
        ApplicationConfig applicationConfig = configRepository.findOne(param);

        if (isNull(applicationConfig)) {
            applicationConfig = new ApplicationConfig();
            applicationConfig.setParam(param);
        }

        applicationConfig.setImage(logo.getBytes());

        ApplicationConfig applicationConfigSaved = configRepository.save(applicationConfig);

        return applicationConfigSaved;
    }

    @Override
    public InputStream getLogoByParam(String param) {
        ApplicationConfig config = configRepository.findByParam(param);

        InputStream is = null;

        if (isNotNull(config.getImage())) {
            Hibernate.initialize(config.getImage());
            is = new ByteArrayInputStream(config.getImage());
        }

        return is;
    }

}