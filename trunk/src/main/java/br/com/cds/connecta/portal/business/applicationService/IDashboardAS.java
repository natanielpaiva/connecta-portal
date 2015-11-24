package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public interface IDashboardAS {
    
    /**
     * Pesquisa visualizadores de todos os módulos por nome ou descrição
     * @param text
     * @return 
     */
    List<Map<String, Object>> searchViewers(String text);
    
    Iterable<Dashboard> list(DashboardPaginationFilter filter);

    Dashboard get(Long id);

    Dashboard save(Dashboard application);

    Dashboard update(Dashboard entity);

    void delete(Long id);

}
