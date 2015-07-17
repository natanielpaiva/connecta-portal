package br.com.cds.connecta.portal.filter;

import br.com.cds.connecta.framework.core.filter.PaginationFilter;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public class DashboardPaginationFilter extends PaginationFilter {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
