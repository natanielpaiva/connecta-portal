package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.security.ldap.LdapUser;

public interface ILdapAS  {

	LdapUser verifyLogin(String user, String passwd );
	LdapUser getByEmail(String email);
	String getEmailByLogin(String login);

}
