package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import static br.com.cds.connecta.framework.core.util.Util.*;
import br.com.cds.connecta.portal.UtilSolr;
import br.com.cds.connecta.portal.business.applicationService.IDashboardAS;
import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.entity.DashboardSection;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;
import br.com.cds.connecta.portal.persistence.DashboardDAO;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    public Iterable<Dashboard> list(DashboardPaginationFilter filter) {
        Pageable pageable = filter.makePageable();
        
        Iterable<Dashboard> page;
        if (!isEmpty(filter.getName())) {
            page = dao.findAllLikeName(prepareForSearch(filter.getName(), true), pageable);
        } else {
            page = dao.findAll(pageable);
        }
        
        return page;
    }

    @Override
    public Dashboard get(Long id) {
        Dashboard dashboard = dao.findOneWithSections(id);
        
        if ( isNull(dashboard) ) {
            throw new ResourceNotFoundException(Dashboard.class.getCanonicalName());
        }
        
        for (DashboardSection section : dashboard.getSections()) {
            Hibernate.initialize(section.getItems());
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

    @Override
    public List<Map<String, Object>> searchViewers(String text) {
        
        return UtilSolr.searchSingleFieldAsMapList("text", text, 10);
    }
    
}
