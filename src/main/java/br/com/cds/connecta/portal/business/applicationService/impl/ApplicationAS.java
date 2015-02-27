package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.portal.business.applicationService.IApplicationAS;
import br.com.cds.connecta.portal.entity.Application;
import br.com.cds.connecta.framework.core.filter.PaginationFilter;
import br.com.cds.connecta.portal.persistence.ApplicationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author pires
 */
@Service
public class ApplicationAS implements IApplicationAS {
    
    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application get(Long id) {
        return applicationRepository.findOne(id);
    }

    @Override
    public List<Application> list() {
        return applicationRepository.findAll();
    }
    
    @Override
    public Page<Application> list(PaginationFilter filter) {
        Pageable pageable = filter.makeSortablePageable();
        
        return applicationRepository.findAll(pageable);
    }

    @Override
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public Application update(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public void delete(Long id) {
        applicationRepository.delete(id);
    }
    
}
