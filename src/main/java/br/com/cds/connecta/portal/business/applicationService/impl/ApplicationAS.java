/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.portal.business.applicationService.IApplicationAS;
import br.com.cds.connecta.portal.dao.ApplicationDAO;
import br.com.cds.connecta.portal.entity.Application;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author pires
 */
@Service
public class ApplicationAS implements IApplicationAS {
    
    @Autowired
    private ApplicationDAO dao;

    @Override
    public Application get(Long id) {
        return dao.get(id);
    }

    @Override
    public List<Application> list() {
        return dao.list();
    }

    @Override
    public Application save(Application application) {
       return dao.saveOrUpdate(application);
    }

    @Override
    public Application update(Application application) {
        return dao.saveOrUpdate(application);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
    
}
