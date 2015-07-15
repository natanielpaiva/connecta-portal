package br.com.cds.connecta.portal.business.applicationService.impl;

import static br.com.cds.connecta.framework.core.util.Util.isNull;
import br.com.cds.connecta.portal.business.applicationService.IDashboardAS;
import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.persistence.DashboardDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
@Service
public class DashboardAS implements IDashboardAS {
    
    @Autowired
    private DashboardDAO dao;
    
    @Override
    public List<Dashboard> list() {
        return dao.findAll();
    }

    @Override
    public Dashboard get(Long id) {
        Dashboard dashboard = dao.findOne(id);
        
        if ( isNull(dashboard) ) {
//            throw new ResourceNotFoundException();
        }
        
        return dashboard;
    }

    @Override
    public Dashboard save(Dashboard dashboard) {
        return dao.save(dashboard);
    }

    @Override
    public Dashboard update(Dashboard dashboard) {
        return dao.save(dashboard);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }
    
}
