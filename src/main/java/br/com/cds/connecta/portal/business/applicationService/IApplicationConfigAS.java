package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.domain.ApplicationConfigEnum;

/**
 *
 * @author Julio Lemes
 * @date Jul 27, 2015
 */
public interface IApplicationConfigAS {

    String getByName(ApplicationConfigEnum config);


}