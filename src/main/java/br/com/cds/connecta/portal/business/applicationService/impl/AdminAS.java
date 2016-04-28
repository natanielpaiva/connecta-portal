package br.com.cds.connecta.portal.business.applicationService.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cds.connecta.portal.business.applicationService.IAdminAS;
import br.com.cds.connecta.portal.business.applicationService.IDomainAS;
import br.com.cds.connecta.portal.business.applicationService.IUserAS;
import br.com.cds.connecta.portal.entity.Domain;
import br.com.cds.connecta.portal.entity.User;
import br.com.cds.connecta.portal.persistence.DomainDAO;

@Service
public class AdminAS implements IAdminAS {

    @Autowired
    private DomainDAO domainRepository;
    
    @Autowired
    private IUserAS userAS;

	@Override
	public List<Domain> getAll() {
		return domainRepository.findAll();
	}

	@Override
	public List<Domain> getByUsername(String username) {
		User usr = userAS.get(new User(username)); // email or login?
		return usr != null ? usr.getDomains() : null;
	}
    
}
