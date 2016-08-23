package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.security.ldap.LdapUser;

public interface ILdapAS  {

	public LdapUser verifyLogin(String user, String passwd );

}
