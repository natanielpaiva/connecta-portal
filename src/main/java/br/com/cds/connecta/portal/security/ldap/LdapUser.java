package br.com.cds.connecta.portal.security.ldap;

import java.util.HashMap;
import java.util.Map;

public class LdapUser {
	
	private String username;
	private String name;
	private Map<String,String> attributes;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Map<String,String> getAttributes() {
		if (attributes==null){
			attributes = new HashMap<String,String>();
		}
		return attributes;
	}
	public void setAttributes(Map<String,String> attributes) {
		this.attributes = attributes;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addAttrbute(String name, String value){
		getAttributes().put(name, value);
	}

}
