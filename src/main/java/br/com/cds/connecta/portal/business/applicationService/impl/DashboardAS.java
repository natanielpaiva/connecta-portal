package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import static br.com.cds.connecta.framework.core.util.Util.*;
import br.com.cds.connecta.portal.util.SolrUtil;
import br.com.cds.connecta.portal.business.applicationService.IDashboardAS;
import br.com.cds.connecta.portal.dto.DashboardDTO;
import br.com.cds.connecta.portal.dto.DashboardItemDTO;
import br.com.cds.connecta.portal.dto.DashboardSectionDTO;
import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.entity.DashboardSection;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;
import br.com.cds.connecta.portal.persistence.DashboardDAO;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Iterable<Dashboard> list(DashboardPaginationFilter filter) {
        Pageable pageable = filter.makePageable();
        
        Iterable<Dashboard> page;

        if (!isEmpty(filter.getName())) {
            page = dao.findByDomainLikeName(prepareForSearch(filter.getDomain(), false)
            		,prepareForSearch(filter.getName(), true), pageable);
        } else {                                                                       
            page = dao.findByDomain(prepareForSearch(filter.getDomain(), false), pageable);
        }

        return page;
    }

    @Override
    public Dashboard get(Long id, String domain) {
        Dashboard dashboard = dao.findOneWithSections(id, domain);

        if (isNull(dashboard)) {
            throw new ResourceNotFoundException(Dashboard.class.getSimpleName());
        }

        for (DashboardSection section : dashboard.getSections()) {
            Hibernate.initialize(section.getItems());
        }

        return dashboard;
    }

    @Override
    public Dashboard save(DashboardDTO dashboardDTO) {
        Dashboard dashboard = convertDTOToEntity(dashboardDTO);
        return dao.save(dashboard);
    }

    @Override
    public Dashboard update(DashboardDTO dashboardDTO) {
        Dashboard dashboard = convertDTOToEntity(dashboardDTO);
        return dao.save(dashboard);
    }

    @Override
    public void delete(Long id) {
        dao.delete(id);
    }

    @Override
    public List<Map<String, Object>> searchViewers(String text) {
        return SolrUtil.searchSingleFieldAsMapList("text", text, 10);
    }

    private Dashboard convertDTOToEntity(DashboardDTO dashboardDTO) {
        Dashboard dashboard = mapper.map(dashboardDTO, Dashboard.class);
        
        if (isNotNull(dashboardDTO.getBackgroundImage())) {
            dashboard.setBackgroundImage(
                dashboardDTO.getBackgroundImage().getBase64()
            );
        }
        
        for (int s = 0; s < dashboardDTO.getSections().size(); s++) {
            DashboardSectionDTO sectionDTO = dashboardDTO.getSections().get(s);
            for (int i = 0; i < sectionDTO.getItems().size(); i++) {
                DashboardItemDTO itemDTO = sectionDTO.getItems().get(i);
                
                if (isNotNull(itemDTO.getBackgroundImage())) {
                    dashboard.getSections().get(s).getItems().get(i).setBackgroundImage(
                        itemDTO.getBackgroundImage().getBase64()
                    );
                }
            }
        }
        
        return dashboard;
    }

}
