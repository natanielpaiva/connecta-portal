package br.com.cds.connecta.portal.business.applicationService.impl;


import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cds.connecta.framework.core.context.ConnectaConfigurationService;
import br.com.cds.connecta.framework.core.context.LdapConfiguration;
import br.com.cds.connecta.framework.core.exception.ResourceNotFoundException;
import br.com.cds.connecta.portal.business.applicationService.ILdapAS;
import br.com.cds.connecta.portal.security.ldap.LdapFilter;
import br.com.cds.connecta.portal.security.ldap.LdapProvider;
import br.com.cds.connecta.portal.security.ldap.LdapProviderException;
import br.com.cds.connecta.portal.security.ldap.LdapUser;

@Service
public class LdapAS implements ILdapAS, Observer {

	private ConnectaConfigurationService connectaConfigurationService;
	
	private LdapConfiguration ldapConfiguration = new LdapConfiguration();
	
	private final Logger logger = Logger.getLogger(LdapAS.class);

	public LdapUser verifyLogin(String username, String password ){
		LdapProvider ldapProvider = getLdapProvider();

		//criando filtro para pesquisa
		LdapFilter filter = new LdapFilter();
		filter.addFilter("objectClass", "organizationalPerson");
		filter.addFilter("sAMAccountName", username);

		LdapUser ldapUser = null;

		try {
			ldapUser = ldapProvider.authenticate(username, password, filter);
		} catch (LdapProviderException e) {
			logger.error(e.getMessage(), e.getCause());
		}

		return ldapUser;
	}
	
	@Override
	public LdapUser getByEmail(String email) {
		LdapProvider ldapProvider = getLdapProvider();
		
		//criando filtro para pesquisa
		LdapFilter filter = new LdapFilter();
		filter.addFilter("objectClass", "organizationalPerson");
		filter.addFilter("mail", email);
		
		LdapUser ldapUser = null;

		try {
			ldapUser = ldapProvider.returnUserByEmail(filter);
		} catch (LdapProviderException e) {
			throw new ResourceNotFoundException(LdapUser.class.getCanonicalName());
		}

		return ldapUser;
	}
	
	@Override
	public String getEmailByLogin(String username) {
		LdapProvider ldapProvider = getLdapProvider();
		
		//criando filtro para pesquisa
		LdapFilter filter = new LdapFilter();
		filter.addFilter("objectClass", "organizationalPerson");
		filter.addFilter("sAMAccountName", username);
		
		String email = null;

		try {
			email = ldapProvider.returnEmailByLogin(filter);
		} catch (LdapProviderException e) {
			throw new ResourceNotFoundException(LdapUser.class.getCanonicalName());
		}

		return email;
	}

	private LdapProvider getLdapProvider() {
		ldapConfiguration = connectaConfigurationService.getConfiguration().getLdapConfiguration();
		
		LdapProvider ldapProvider = new LdapProvider();

		ldapProvider.setAuthmode(ldapConfiguration.getAuthMode());
		ldapProvider.setServerbase(ldapConfiguration.getLdapBase());
		ldapProvider.setServername(ldapConfiguration.getLdapUrl());
		ldapProvider.setUsername(ldapConfiguration.getUser());
		ldapProvider.setPassword(ldapConfiguration.getPassword());
		
		//atributos a serem retornados
		ldapProvider.addAttributes2Return("CN");
		ldapProvider.addAttributes2Return("sAMAccountName");
		ldapProvider.addAttributes2Return("mail");
		
		return ldapProvider;
	}
	
    @Autowired
    public void setConnectaConfigurationService(ConnectaConfigurationService connectaConfigurationService) {
        this.connectaConfigurationService = connectaConfigurationService;
        this.connectaConfigurationService.addObserver(this);
    }

    @Override
    public void update(Observable o, Object o1) {
        ldapConfiguration = connectaConfigurationService.getConfiguration().getLdapConfiguration();
    }


}
