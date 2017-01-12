package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;
import br.com.cds.connecta.portal.entity.ApplicationConfig;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Julio Lemes
 * @author Heloisa
 * @date Jul 27, 2015
 */
public interface IApplicationConfigAS {

    String getByName(ApplicationConfigEnum config);

    ApplicationConfig uploadLogo(String param, MultipartFile logo) throws IOException;

    InputStream getLogoByParam(String param);

}