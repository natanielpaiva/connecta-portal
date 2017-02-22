package br.com.cds.connecta.portal.business.applicationService.impl;

import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.framework.core.util.Util;
import static br.com.cds.connecta.framework.core.util.Util.isNull;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.DomainRepository;

/**
 *
 * @author heloisa
 */
@Service
public class DomainAS implements IDomainAS {

    @Autowired
    private DomainRepository domainRepository;

    @Autowired
    private IUserAS userAS;

    @Override
    public List<Domain> getAll() {
        return domainRepository.findAll();
    }

    @Override
    public List<Domain> getByUser(String username) {
        User usr = userAS.getByEmail(username);
        return usr != null ? usr.getDomains() : null;
    }

    @Override
    public Domain save(Domain domain) {
        return domainRepository.save(domain);
    }

    @Override
    public Domain update(Domain domain) {
        return  domainRepository.save(domain);
    }
    
    @Override
    public Domain removeUser(Long idDomain, Long idUser) {
        Domain domain = getById(idDomain);
        domain.getUsers().remove(userAS.get(idUser));
        
        return domainRepository.save(domain);
    }

    @Override
    public void delete(Long id) {
        get(id);
        domainRepository.delete(id);
    }

    @Override
    public Domain get(Long id) {
        Domain domain = domainRepository.findOne(id);
        if (Util.isNull(domain)) {
            throw new ResourceNotFoundException(Domain.class.getCanonicalName());
        }
        return domain;
    }

    @Override
    public Domain getById(Long id) {
        Domain domain = domainRepository.findOne(id);
        
        if(isNull(domain)){
            throw new ResourceNotFoundException(Domain.class.getSimpleName());
        }
        
        return domain;
    }

    @Override
    public List<User> getParticipants(Long id) {
        Domain domain = domainRepository.findOne(id);
        
        return domain.getUsers();
    }

}
