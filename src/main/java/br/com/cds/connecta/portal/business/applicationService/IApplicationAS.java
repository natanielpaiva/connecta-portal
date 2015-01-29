/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.Application;
import java.util.List;

/**
 *
 * @author pires
 */
public interface IApplicationAS {

    public Application get(Long id);

    public List<Application> list();

    public Application save(Application application);

    public Application update(Application application);

    public void delete(Long id);
    
}
