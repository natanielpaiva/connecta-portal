package br.com.cds.connecta.portal.filter;

import br.com.cds.connecta.framework.core.search.filter.PaginationFilter;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class DashboardPaginationFilter extends PaginationFilter {
    private String name;
    private String domain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
