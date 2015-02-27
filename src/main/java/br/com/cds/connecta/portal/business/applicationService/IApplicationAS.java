/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.Application;
import br.com.cds.connecta.framework.core.filter.PaginationFilter;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 *
 * @author pires
 */
public interface IApplicationAS {

    Application get(Long id);

    List<Application> list();
    
    Page<Application> list(PaginationFilter filter);

    Application save(Application application);

    Application update(Application application);

    void delete(Long id);
    
}
