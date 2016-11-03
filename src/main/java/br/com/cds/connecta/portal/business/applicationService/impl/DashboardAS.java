package br.com.cds.connecta.portal.business.applicationService.impl;

import static br.com.cds.connecta.framework.core.util.Util.isEmpty;
import static br.com.cds.connecta.framework.core.util.Util.isNotNull;
import static br.com.cds.connecta.framework.core.util.Util.isNull;
import static br.com.cds.connecta.framework.core.util.Util.prepareForSearch;

import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.framework.core.exception.SystemException;
import br.com.cds.connecta.framework.core.security.KeyPairGeneratorUtil;
import br.com.cds.connecta.framework.core.util.Util;
import br.com.cds.connecta.portal.business.applicationService.IDashboardAS;
import br.com.cds.connecta.portal.dto.DashboardDTO;
import br.com.cds.connecta.portal.dto.DashboardItemDTO;
import br.com.cds.connecta.portal.dto.DashboardSectionDTO;
import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.entity.DashboardItem;
import br.com.cds.connecta.portal.entity.DashboardPublic;
import br.com.cds.connecta.portal.entity.DashboardSection;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;
import br.com.cds.connecta.portal.persistence.DashboardRepository;
import br.com.cds.connecta.portal.persistence.specification.DashboardSpecification;
import br.com.cds.connecta.portal.util.SolrUtil;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
@Service
public class DashboardAS implements IDashboardAS {

    @Autowired
    private DashboardRepository dashboardRepository;

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public Iterable<Dashboard> list(DashboardPaginationFilter filter) {
        Pageable pageable = filter.makePageable();

        Iterable<Dashboard> page;

        if (!isEmpty(filter.getName())) {
            page = dashboardRepository.findByDomainLikeName(
                    filter.getDomain(),
                    prepareForSearch(filter.getName(), true),
                    pageable);
        } else {
            page = dashboardRepository.findByDomain(filter.getDomain(), pageable);
        }

        return page;
    }

    @Override
    public Dashboard get(Long id, String domain) {
        Dashboard dashboard = dashboardRepository.findOneWithSections(id, domain);

        if (isNull(dashboard)) {
            throw new ResourceNotFoundException(Dashboard.class.getSimpleName());
        }

        for (DashboardSection section : dashboard.getSections()) {
            Hibernate.initialize(section.getItems());
        }

        return dashboard;
    }
    
    @Override
    public Dashboard getPublic(Long id) {
        Dashboard dashboard = dashboardRepository.findOneWithSectionsPublic(id);
        
        if (isNull(dashboard)) {
            throw new ResourceNotFoundException(Dashboard.class.getSimpleName());
        }

        for (DashboardSection section : dashboard.getSections()) {
            Hibernate.initialize(section.getItems());
        }

        return dashboard;
    }

    @Override
    public Dashboard save(DashboardDTO dashboardDTO){
        Dashboard dashboard = convertDTOToEntity(dashboardDTO);
        if(dashboard.isPublic()){
        	generatePrivateKey(dashboard);
        }
        return dashboardRepository.save(dashboard);
    }

	@Override
    public Dashboard update(DashboardDTO dashboardDTO) {
        Dashboard dashboard = convertDTOToEntity(dashboardDTO);
        return dashboardRepository.save(dashboard);
    }

    @Override
    public void delete(Long id) {
        dashboardRepository.delete(id);
    }
    
    @Override
    public void deleteAll(List<Long> ids, String domain) {
    	List<Dashboard> listDs = dashboardRepository.findAll(DashboardSpecification.byIdsAndDomain(ids, domain));
    	dashboardRepository.delete(listDs);
    }

    @Override
    public List<Map<String, Object>> searchViewers(String text, String domain) {
        return SolrUtil.searchSingleFieldAsMapList("text", text, 10, domain);
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
        
        if(!dashboard.isPublic()){
        	dashboard.setPublicDashboard(null);
        }else if(dashboard.isPublic() && 
        		(Util.isNull(dashboard.getPublicDashboard()) 
        				|| Util.isNull(dashboard.getPublicDashboard().getId()))){
        	generatePrivateKey(dashboard);
        }
        
        return dashboard;
    }
    
    private void generatePrivateKey(Dashboard dashboard){
    	try{
        	DashboardPublic dp = new DashboardPublic();
        	
    		KeyPairGeneratorUtil kpg = new KeyPairGeneratorUtil();
    		byte[] encryptedString = kpg.encrypt(DashboardPublic.PUBLIC_DASHBOARD);
    		
    		dp.setPublicKey(kpg.getKeyPair().getPublic().getEncoded());
    		dp.setPrivateKeyEncodedString(encryptedString);
        	dashboard.setPublicDashboard(dp);
    	}catch(Exception e){
    		throw new SystemException(e);
    	}
	}

	@Override
	public boolean validatePublicKey(Long publicKey, Long viewerId) {
		Dashboard dashboard = dashboardRepository.findOneByPublicKey(publicKey);
        if (isNull(dashboard) || !validateKey(dashboard)) {
            return false;
        }
        for (DashboardSection section : dashboard.getSections()) {
            Hibernate.initialize(section.getItems());
            for(DashboardItem item : section.getItems()){
            	if(item.getViewer().equals(viewerId)){
            		return true;
            	}
            }
        }
		return false;
	}

	@Override
	public boolean validatePublicKeyDash(Long publicKey, Long dashId) {
		Dashboard dashboard = dashboardRepository.findOneByPublicKeyAndId(dashId, publicKey);
        if (isNull(dashboard) || !validateKey(dashboard)) {
            return false;
        }
		return true;
	}
	
	private boolean validateKey(Dashboard dashboard) {
		try {
			String decryptedMessage = 
					KeyPairGeneratorUtil.decrypt(dashboard.getPublicDashboard().getPrivateKeyEncodedString(), 
							dashboard.getPublicDashboard().getPublicKey());
			
			if(!DashboardPublic.PUBLIC_DASHBOARD.equals(decryptedMessage))
				return false;
		} catch (SystemException e) {
			return false;
		}
		
		return true;
	}

}
