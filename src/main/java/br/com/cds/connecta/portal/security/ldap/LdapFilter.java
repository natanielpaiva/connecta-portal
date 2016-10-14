package br.com.cds.connecta.portal.security.ldap;

public class LdapFilter {	
	
	private StringBuffer sbFilter;
	
	public LdapFilter(){
		this.sbFilter = new StringBuffer();
	}
	
	
	public void addFilter(String attribute, String value){		
		sbFilter.append("(" + attribute + "=" + value +")");
	}
	
	protected String getStringFilter(){
		
		String strFilter = "(&#)";
		
		if (sbFilter.length()>0){
			return strFilter.replace("#", sbFilter.toString()) ;
		}
		return null;
	}

}
