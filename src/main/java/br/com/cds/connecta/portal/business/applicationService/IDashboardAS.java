package br.com.cds.connecta.portal.business.applicationService;

import java.util.List;
import java.util.Map;

import br.com.cds.connecta.portal.dto.DashboardDTO;
import br.com.cds.connecta.portal.entity.Dashboard;
import br.com.cds.connecta.portal.filter.DashboardPaginationFilter;

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
    List<Map<String, Object>> searchViewers(String text, String domain);
    
    Iterable<Dashboard> list(DashboardPaginationFilter filter);

    Dashboard get(Long id, String domain);
    
    Dashboard getPublic(Long id);

    Dashboard save(DashboardDTO application);

    Dashboard update(DashboardDTO entity);

    void delete(Long id);
    
    void deleteAll(List<Long> ids, String domain);
    
    boolean validatePublicKey(Long publicKey, Long viewerId);
    
    public boolean validatePublicKeyDash(Long publicKey, Long dashId);

}
