package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.exception.AlreadyExistsException;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.framework.core.util.Util;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.DomainRepository;

@Service
public class DomainAS implements IDomainAS {
    
    @Autowired
    private DomainRepository domainRepository;
    
    @Autowired
    private IUserAS userAS;

    /**
     *
     * @return
     */
    @Override
    public List<Domain> getAll() {
        return domainRepository.findAll();
    }

    /**
     *
     * @param username
     * @return
     */
    @Override
    public List<Domain> getByUser(String username) {
        User usr = userAS.getByEmail(username); // email or login?
        return usr != null ? usr.getDomains() : null;
    }

    /**
     *
     * @param domain
     * @return
     */
    @Override
    public Domain save(Domain domain) {
        
        Domain exists = domainRepository.findByName(domain.getName());
        if (Util.isNotNull(exists)) {
            throw new AlreadyExistsException("name", "name");
        }
        return domainRepository.save(domain);
    }
    
    @Override
    public Domain update(Domain domain) {
        return (Domain) domainRepository.save(domain);
    }
    
    @Override
    public void delete(Long id) {
        get(id);
        domainRepository.delete(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Domain get(Long id) {
        Domain domain = domainRepository.getOne(id);
        if (Util.isNull(domain)) {
            throw new ResourceNotFoundException(Domain.class.getCanonicalName());
        }
        return domain;
    }
    
}
