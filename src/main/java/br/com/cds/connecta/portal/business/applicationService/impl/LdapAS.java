package br.com.cds.connecta.portal.business.applicationService.impl;


import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Service;

import br.com.cds.connecta.portal.business.applicationService.ILdapAS;
import br.com.cds.connecta.portal.security.ldap.LdapFilter;
import br.com.cds.connecta.portal.security.ldap.LdapProvider;
import br.com.cds.connecta.portal.security.ldap.LdapProviderException;
import br.com.cds.connecta.portal.security.ldap.LdapUser;

@Service
public class LdapAS implements ILdapAS {

	private Properties props = null;

	private static String PROP_LDAP_SERVER = "LDAP_URL";
	private static String PROP_LDAP_BASE = "LDAP_BASE";
	private static String PROP_LDAP_AUTHMODE = "LDAP_AUTHMODE";
	private static String PROP_LDAP_USER = "LDAP_USER";
	private static String PROP_LDAP_PASSWORD = "LDAP_PASSWORD";

	public LdapAS() throws IOException{
		props = new Properties();
		props.load(getClass().getClassLoader().getResourceAsStream("ldap.properties"));
	}

	public LdapUser verifyLogin(String username, String password ){

		LdapProvider ldapProvider = new LdapProvider();

		ldapProvider.setAuthmode( props.getProperty(PROP_LDAP_AUTHMODE));
		ldapProvider.setServerbase( props.getProperty(PROP_LDAP_BASE));
		ldapProvider.setServername( props.getProperty(PROP_LDAP_SERVER));
		ldapProvider.setUsername( props.getProperty(PROP_LDAP_USER));
		ldapProvider.setPassword( props.getProperty(PROP_LDAP_PASSWORD));

		//atributos a serem retornados
		ldapProvider.addAttributes2Return("CN");
		ldapProvider.addAttributes2Return("sAMAccountName");

		//criando filtro para pesquisa
		LdapFilter filter = new LdapFilter();
		filter.addFilter("objectClass", "organizationalPerson");
		filter.addFilter("sAMAccountName", username);

		LdapUser ldapUser=null;

		try {
			ldapUser = ldapProvider.authenticate(username, password, filter);
		} catch (LdapProviderException e) {
			e.printStackTrace();
		}

		return ldapUser;

	}

}
