package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public interface IDashboardAS {
    
    Iterable<Dashboard> list(DashboardPaginationFilter filter);

    Dashboard get(Long id);

    Dashboard save(Dashboard application);

    Dashboard update(Dashboard entity);

    void delete(Long id);

}
