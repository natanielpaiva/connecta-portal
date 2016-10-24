package br.com.cds.connecta.portal.security.ldap;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import br.com.cds.connecta.framework.core.domain.MessageEnum;
import br.com.cds.connecta.framework.core.exception.BusinessException;

public class LdapProvider {	
	
	private String servername = null;
	private String serverbase = null;
	private String username = null;
	private String password = null;
	private String authmode = "simple";		
	
	private List<String> attributes2Return;
	
	private Attributes ldapAttributes;
	
	public LdapProvider(){
		attributes2Return = new ArrayList<String>();
		attributes2Return.add("distinguishedName");
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getServerbase() {
		return serverbase;
	}

	public void setServerbase(String serverbase) {
		this.serverbase = serverbase;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAuthmode() {
		return authmode;
	}

	public void setAuthmode(String authmode) {
		this.authmode = authmode;
	}
	
	public void addAttributes2Return(String attribute){
		attributes2Return.add(attribute);
	}

	/*
	 * Cria um contexto com o servidor LDAP
	 */
	private LdapContext getLDAPContext() throws NamingException{
		
		Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_AUTHENTICATION, getAuthmode());
        
        if(username != null) {
            env.put(Context.SECURITY_PRINCIPAL, getUsername());
        }
        
        if(password != null) {
            env.put(Context.SECURITY_CREDENTIALS, getPassword());
        }
        
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, servername );

        // the following is helpful in debugging errors
        //env.put("com.sun.jndi.ldap.trace.ber", System.err);
        
        LdapContext authContext = new InitialLdapContext(env,null);			
		return authContext;				
		
		
	}
	
	public LdapUser authenticate(String username, String password, LdapFilter filter ) throws LdapProviderException{
		
		LdapUser ldapUser = new LdapUser();
		String searchFilter = filter.getStringFilter();
		LdapContext ctx;
		
		try {
			ctx = getLDAPContext();
		} catch (NamingException ex) {
			throw new LdapProviderException(ex.getMessage(), ex.getCause());
		}
		
		SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        //Specify the attributes to return
        searchControls.setReturningAttributes(attributes2Return.toArray(new String[attributes2Return.size()]));
        NamingEnumeration<SearchResult> results;
		
        try {
			
        	results = ctx.search(getServerbase(), searchFilter, searchControls);        	
        	
			if(results.hasMoreElements()) {
	             
	        	 SearchResult result = (SearchResult) results.next();
	        	 ldapAttributes = result.getAttributes();
	             
	             //Recuperando o atributo que possui o endere�o completo do usu�rio
	        	 String dn = getLdapAttributeValue("distinguishedName");

	             // User Exists, Validate the Password
	             Hashtable<String, Object> env = new Hashtable<String, Object>();
	             env.put(Context.SECURITY_AUTHENTICATION, getAuthmode());	             
	             env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	             env.put(Context.PROVIDER_URL, getServername() );
	             env.put(Context.SECURITY_PRINCIPAL, dn);
	             env.put(Context.SECURITY_CREDENTIALS, password);

	             DirContext dirCtx = new InitialDirContext(env);
	             
	             //atribuindo atributos ao usuario LDAP
	             ldapUser.setUsername(username);
	             ldapUser.setName(getLdapAttributeValue("CN"));
	             
	             //recuperando os atributos retornados e adicionando ao usuario LDAP
	             NamingEnumeration<String> attributeNames = ldapAttributes.getIDs();
	             
	             while (attributeNames.hasMoreElements()){
	            	 String attributeName = attributeNames.next();
	            	 String attributeValue = getLdapAttributeValue(attributeName);
	            	 
	            	 ldapUser.addAttrbute(attributeName, attributeValue);
	             }
	             
	             
	             dirCtx.close();
	             ctx.close();	             	               
	        }
			
		} catch (NamingException e) {			
			throw new LdapProviderException(e.getMessage(), e.getCause());
		}finally{
			try {
				ctx.close();
			} catch (NamingException e) {
				throw new BusinessException(MessageEnum.SYSTEM_ERROR);
			}
		}

        return ldapUser;        
	}
	
	public LdapUser returnUserByEmail(LdapFilter filter) throws LdapProviderException{
		LdapUser ldapUser = null;
		String searchFilter = filter.getStringFilter();
		LdapContext ctx;
		
		try {
			ctx = getLDAPContext();
		} catch (NamingException ex) {
			throw new LdapProviderException(ex.getMessage(), ex.getCause());
		}
		
		SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        //Specify the attributes to return
        searchControls.setReturningAttributes(attributes2Return.toArray(new String[attributes2Return.size()]));
        NamingEnumeration<SearchResult> results;
		
        try {
        	results = ctx.search(getServerbase(), searchFilter, searchControls);        	
        	
			if(results.hasMoreElements()) {
				ldapUser = new LdapUser();
				
				SearchResult result = (SearchResult) results.next();
	        	ldapAttributes = result.getAttributes();
	        	
				//atribuindo atributos ao usuario LDAP
				ldapUser.setUsername(getLdapAttributeValue("sAMAccountName"));
				ldapUser.setName(getLdapAttributeValue("CN"));
			}
			
        } catch (NamingException e) {			
			throw new LdapProviderException(e.getMessage(), e.getCause());
		}finally{
			try {
				ctx.close();
			} catch (NamingException e) {
				throw new BusinessException(MessageEnum.SYSTEM_ERROR);
			}
		}
        
        return ldapUser;
	}
	
	public String returnEmailByLogin(LdapFilter filter) throws LdapProviderException{
		String email = null;
		String searchFilter = filter.getStringFilter();
		LdapContext ctx;
		
		try {
			ctx = getLDAPContext();
		} catch (NamingException ex) {
			throw new LdapProviderException(ex.getMessage(), ex.getCause());
		}
		
		SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        
        //Specify the attributes to return
        searchControls.setReturningAttributes(attributes2Return.toArray(new String[attributes2Return.size()]));
        NamingEnumeration<SearchResult> results;
		
        try {
        	results = ctx.search(getServerbase(), searchFilter, searchControls);        	
        	
			if(results.hasMoreElements()) {
				SearchResult result = (SearchResult) results.next();
	        	ldapAttributes = result.getAttributes();
	        	
				//atribuindo atributos ao usuario LDAP
	        	email = getLdapAttributeValue("mail");
			}
        } catch (NamingException e) {			
			throw new LdapProviderException(e.getMessage(), e.getCause());
		}finally{
			try {
				ctx.close();
			} catch (NamingException e) {
				throw new BusinessException(MessageEnum.SYSTEM_ERROR);
			}
		}
        
        return email;
	}
	
	private String getLdapAttributeValue(String attributeName){
		String attribute = null;
		
		try {
			attribute =(String) ldapAttributes.get(attributeName).get();
		} catch (NamingException e) {
			throw new BusinessException(MessageEnum.SYSTEM_ERROR);
		}
		
		return attribute;
	}
	
}
