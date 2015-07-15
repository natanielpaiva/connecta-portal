package br.com.cds.connecta.portal.business.applicationService;

import br.com.cds.connecta.portal.entity.Dashboard;
import java.util.List;

/**
 *
 * @author Vinicius Pires <vinicius.pires@cds.com.br>
 */
public interface IDashboardAS {
    
    List<Dashboard> list();

    Dashboard get(Long id);

    Dashboard save(Dashboard application);

    Dashboard update(Dashboard entity);

    void delete(Long id);

}
