package br.com.cds.connecta.portal.security.ldap;

public class LdapProviderException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public LdapProviderException(){
		
	}
	
	public LdapProviderException(String message){
		super(message);
	}
	
	public LdapProviderException(Throwable cause){
		super(cause);
	}
	
	public LdapProviderException(String message, Throwable cause){
		super(message,cause);
	}

}
